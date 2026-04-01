package com.general.service;

import com.general.model.maritime.request.*;
import com.general.model.maritime.response.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MaritimeService {

    /**
     * Padroniza contêineres para cotação frete
     * @param req TeuRequest
     * @return double
     */
    public double calcularTeu(TeuRequest req) {
        return req.qtd20ft() * 1.0 +
                req.qtd40ft() * 2.0 +
                req.qtd40high() * 2.25 +
                req.qtd45ft() * 2.5;
    }

    /**
     * Sobretaxa combustível (45% custo navio!)
     * @param req BafRequest
     * @return double
     */
    public double calcularBaf(BafRequest req) {
        double bunkerTotal = req.distanciaNm() * req.consumoPorMilha() * req.precoBunkerUsd();
        return bunkerTotal / req.teuCarga(); // USD/TEU
    }

    /**
     * Multa atraso contêiner (R$500/dia!)
     * @param req DemurrageRequest
     * @return MultaResponse
     */
    public MultaResponse calcularDemurrage(DemurrageRequest req) {
        // DEMURRAGE (porto)
        int diasDemurrage = Math.max(0, req.diasPorto() - req.diasFreePorto());
        double valorDemurrage = diasDemurrage * req.taxaDemurrage();

        // DETENTION (terminal)
        int diasDetention = Math.max(0, req.diasTerminal() - req.diasFreeTerminal());
        double valorDetention = diasDetention * req.taxaDetention();

        double total = valorDemurrage + valorDetention;

        String urgencia = total > 1000 ? "🚨 URGENTE: R$500/dia após free time" : "OK";

        return new MultaResponse(
                total,
                valorDemurrage,
                valorDetention,
                String.format("%d dias porto + %d dias terminal",
                        req.diasFreePorto(), req.diasFreeTerminal()),
                urgencia
        );
    }

    /**
     * Peso total navio+carga (estabilidade)
     * @param req DeslocamentoRequest
     * @return DeslocamentoResponse
     */
    public DeslocamentoResponse calcularDeslocamento(DeslocamentoRequest req) {
        // PESOS ajustados por densidade
        double pesoBunker = req.bunkerTon() * 1.02;     // Fuel oil 1.02 ton/m³
        double pesoAgua = req.aguaBalastTon() * 1.025;  // Água do mar

        // TOTAL DESLOCAMENTO
        double total = req.pesoCascoVazio() + pesoBunker + pesoAgua + req.cargaTon();

        double caladoEstimado = total / 2.8; // 2.8m²/m linha água navio médio

        String estabilidade = caladoEstimado < 13.5 ? "OK" : "🚨 SOBRECARGA";
        String aviso = String.format("Calado %.1fm (Máx Santos 13.5m)", caladoEstimado);

        return new DeslocamentoResponse(
                total,
                req.pesoCascoVazio(),
                req.cargaTon(),
                pesoBunker + pesoAgua,
                estabilidade,
                aviso
        );
    }

    /**
     * Importador/exportador discute "FOB ou CIF?" → API decide quem paga o quê!
     * @param req IncotermsRequest
     * @return IncotermsResponse
     */
    public IncotermsResponse calcularIncoterms(IncotermsRequest req) {
        double fob = req.valorFobUsd();
        double frete = req.freteMaritimoUsd();
        double seguro = req.seguroUsd();

        switch (req.incoterm()) {
            case "EXW" -> {  // EX Works
                return new IncotermsResponse("Nada", "Tudo", fob, fob+frete+seguro+10000, "Fábrica", "Exportador arrisca zero");
            }
            case "FOB" -> {  // Free On Board
                return new IncotermsResponse("Até porto", "Frete+Impostos", fob, frete+seguro+10000, "Porto origem", "Padrão BR");
            }
            case "CIF" -> {  // Cost Insurance Freight
                return new IncotermsResponse("Frete+Seguro", "Desembaraço", fob+frete+seguro, 10000, "Porto destino", "Importador fácil");
            }
            case "DAP" -> {  // Delivered At Place
                return new IncotermsResponse("Até destino", "Desembaraço", fob+frete+seguro, 10000, "Porto destino", "Quase DDP");
            }
            default -> throw new IllegalArgumentException("Incoterm inválido");
        }
    }

    /**
     * Calcula tempo permitido de carga/descarga do navio sem multa de Demurrage
     * @param req LaytimeRequest
     * @return LaytimeResponse
     */
    public LaytimeResponse calcularLaytime(LaytimeRequest req) {
        double horasPermitidas = getHorasPermitidas(req);
        double diasTotais = horasPermitidas / 24.0;

        // DEMURRAGE $10k/dia
        double demurrageRisco = diasTotais * 10000;

        String tempo = String.format("%.0fh (%.0fd %.0fh)",
                horasPermitidas,
                Math.floor(diasTotais),
                horasPermitidas % 24);

        return new LaytimeResponse(
                horasPermitidas,
                diasTotais,
                tempo,
                demurrageRisco,
                req.tipoLaytime()
        );
    }

    private static double getHorasPermitidas(LaytimeRequest req) {
        double horasBase = req.tonelagem() / req.taxaTonHora();
        double freeHoras = req.diasFree() * 24.0;
        double laytimeTotalHoras = horasBase + freeHoras;

        double fator;
        if ("RUNNING".equals(req.tipoLaytime())) {
            fator = 1.0;
        } else if ("WWDSHINC".equals(req.tipoLaytime())) {
            fator = 1.0;
        } else {  // WWDSHEX Santos padrão
            fator = 0.85;
        }

        return laytimeTotalHoras * fator;
    }

    /**
     * Calcula taxas movimentação contêiner
     * @param req ThcRequest
     * @return ThcResponse
     */
    public ThcResponse calcularThc(ThcRequest req) {
        // Exemplo: TABELA SANTOS 2026 (fonte ANTAQ real)
        double precoUnitario = switch (req.tipoContainer()) {
            case "20seco" -> 180.0;
            case "40seco" -> 360.0;
            case "20reefer" -> 400.0;
            case "40reefer" -> 650.0;
            default -> 180.0;
        };

        // THC2 +25% (judicialmente questionada)
        if (req.thc2()) precoUnitario *= 1.25;

        double totalUsd = req.quantidade() * precoUnitario;
        double totalReal = totalUsd * 5.5; // USD→BRL 2026

        String breakdown = String.format("%dx %s US$%.0f = US$%.0f",
                req.quantidade(), req.tipoContainer(), precoUnitario, totalUsd);

        String thc2aviso = req.thc2() ? "🚨 THC2 em disputa STJ/TCU" : "THC1 padrão";

        return new ThcResponse(precoUnitario, totalUsd, totalReal, breakdown, thc2aviso);
    }
}
