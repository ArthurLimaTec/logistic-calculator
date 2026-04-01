package com.general.service;

import com.general.model.road.request.*;
import com.general.model.road.response.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoadService {

    /**
     * Calcula o preço do Frete Total por Km
     * @param request CustoPorKmRequest
     * @return CustoPorKmResponse + 20% de lucro
     */
    public CustoPorKmResponse calcularCustoKm(CustoPorKmRequest request) {

        double totalMensal = request.dieselMensal() + request.salario() + request.manutencao() + request.outrosFixos() + request.pedagioMedio();

        double custoPorKm = totalMensal / request.kmMensal();

        double custoTonKm = custoPorKm / 5.0; // Assume caminhão 5ton média

        double lucro20 = custoPorKm * 1.20;
        String rec = String.format("Cobre R$%.2f/km (20%% lucro)", lucro20);

        return new CustoPorKmResponse(custoPorKm, custoTonKm, rec);
    }

    /**
     * Cotação completa Brasil (usado por transportadoras)
     * @param request FreteRequest
     * @return FreteResponse
     */
    public FreteResponse calcularFrete(FreteRequest request) {
        // Frete por KM rodado
        double freteKm = (request.km() * request.custoPorKm()) + request.taxaCarga();

        // Frete por TONELADA-KM (mais usado para longas distâncias)
        double custoTonKm = request.custoPorKm() / 5.0; // 5ton média
        double freteTonKmTotal = request.toneladas() * request.km() * custoTonKm;

        // Maior valor = frete final (protege transportadora)
        double freteFinal = Math.max(freteKm, freteTonKmTotal);

        // ICMS exemplo SP 12%
        double comIcms = freteFinal * 1.12;

        String metodo = freteTonKmTotal > freteKm ? "Ton-Km" : "KM rodado";
        String rec = String.format("Adicione 12%% ICMS = R$%.2f", comIcms);

        return new FreteResponse(
                freteFinal,
                request.custoPorKm(),
                custoTonKm,
                metodo,
                rec
        );
    }

    /**
     * Calcula PESO CUBADO (truque de transportadoras para cobrar pelo maior peso)
     * @param request PesoCubadoRequest
     * @return PesoCubadoResponse
     */
    public PesoCubadoResponse calcularPesoCubado(PesoCubadoRequest request) {
        // VOLUME em m³: C x L x A
        double volume = request.comprimento() * request.largura() * request.altura();

        // PESO CUBADO = volume × fator (300 rodoviário BR)
        double pesoCubado = volume * request.fatorCubagem();

        // SEMPRE COBRA O MAIOR (truque das transportadoras)
        double pesoFinal = Math.max(request.pesoReal(), pesoCubado);

        double multi = pesoFinal / request.pesoReal();
        String dica = String.format("Cobre %.0fx mais caro! Fatura R$%.2f extra",
                multi, (pesoFinal - request.pesoReal()) * 1.68);

        return new PesoCubadoResponse(volume, pesoCubado, pesoFinal, dica, multi);
    }

    /**
     * Peso Cubado + Custo/km
     * @param req FreteIntegradoRequest
     * @return FreteIntegradoResponse
     */
    public FreteIntegradoResponse calcularFreteIntegrado(FreteIntegradoRequest req) {
        // Calcula custo/km dos gastos informados
        double totalGastos = req.dieselMensal() + req.salario() + req.manutencao();
        double custoKm = totalGastos / req.kmMensal();
        double custoComLucro = custoKm * 1.20; // 20% lucro

        // Peso cubado
        double volume = req.comp() * req.larg() * req.alt();
        double pesoCubado = volume * 300; // Rodoviário
        double pesoFinal = Math.max(req.pesoReal(), pesoCubado);

        // Frete final = Peso × km × custo/km + taxa
        double freteBase = pesoFinal * req.km() * (custoComLucro / 1000) + req.taxaCarga();
        double freteIcms = freteBase * 1.12;

        double multi = pesoCubado / req.pesoReal();
        String estrategia = multi > 1 ? "PESO CUBADO (+" + String.format("%.0f", multi) + "x)" : "Peso real";

        return new FreteIntegradoResponse(custoKm, pesoFinal, freteBase, freteIcms, estrategia,
                String.format("Cotação: R$%.2f - %.0fkg %dkm", freteIcms, pesoFinal/1000, (int)req.km()));
    }

    /**
     * EQQ - diz exatamente quanto comprar para repor estoque
     * @param req EOQRequest
     * @return EOQResponse
     */
    public EOQResponse calcularEOQ(EOQRequest req) {
        double eoq = Math.sqrt((2 * req.demandaAnual() * req.custoPedido()) / req.custoEstoqueUnid());

        int pedidosAno = (int) Math.round(req.demandaAnual() / eoq);
        double diasEntrePedidos = 365.0 / pedidosAno;

        double custoPedidos = pedidosAno * req.custoPedido();
        double custoEstoque = (eoq / 2) * req.custoEstoqueUnid();
        double totalAno = custoPedidos + custoEstoque;

        String dica = String.format("🛒 Compre %.0f und a cada %.0f dias", eoq, diasEntrePedidos);

        return new EOQResponse(Math.round(eoq), pedidosAno, diasEntrePedidos, totalAno, dica);
    }

    /**
     * Lei do Frete Mínimo - LEI 13.703/2018: Ninguém pode cobrar menos, sob pena de multa R$550-R$10.500
     * @param req AnttRequest
     * @return AnttResponse
     */
    public AnttResponse calcularAntt(AnttRequest req) {
        // TABELAS ANTT 2026 EMBUTIDAS (Portaria 4)
        double[][] tabelaCCD = {  // [tipo][eixos-2]
                {4.50, 3.20, 2.80, 2.50, 2.20, 2.00, 1.90}, // Carga Geral
                {5.20, 3.70, 3.20, 2.90, 2.60, 2.40, 2.30}  // Granel etc
        };

        // Busca coeficientes por tipo e eixos
        int tipoIdx = switch(req.tipoCarga()) {
            case "geral" -> 0; case "granel" -> 1; default -> 0;
        };
        int eixoIdx = Math.min(req.eixos() - 2, 6);

        double ccd = tabelaCCD[tipoIdx][eixoIdx];
        double cc = 450.0; // Fixo simplificado

        double freteMin = (req.distanciaKm() * ccd) + cc;

        return new AnttResponse(ccd, cc, freteMin,
                String.format("🚨 MULTA R$550 se cobrar menos que R$%.2f", freteMin),
                "Tabela A Portaria ANTT 4/2026");
    }

    /**
     * Determina quanto produzir em lotes
     * @param req EPQRequest
     * @return EPQResponse
     */
    public EPQResponse calcularEPQ(EPQRequest req) {
        // CORREÇÃO pela produção finita (D/P < 1 obrigatório)
        double rho = req.demandaAnual() / req.taxaProducaoAnual();
        if (rho >= 1) throw new IllegalArgumentException("Produção deve ser > demanda!");

        double epq = Math.sqrt((2 * req.demandaAnual() * req.custoSetup()) /
                (req.custoEstoqueUnid() * (1 - rho)));

        int lotesAno = (int) Math.round(req.demandaAnual() / epq);
        double diasEntreLotes = 365.0 / lotesAno;

        // Estoque máximo = EPQ × (1 - D/P)
        double estoqueMax = epq * (1 - rho);

        double custoTotal = (lotesAno * req.custoSetup()) +
                ((epq / 2 * (1 - rho)) * req.custoEstoqueUnid());

        return new EPQResponse(epq, lotesAno, diasEntreLotes, estoqueMax, custoTotal,
                String.format("🏭 Produza %.0f pares a cada %.0f dias", epq, diasEntreLotes));
    }
}
