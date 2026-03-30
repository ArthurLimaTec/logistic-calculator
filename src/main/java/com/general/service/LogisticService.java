package com.general.service;

import com.general.model.request.CustoPorKmRequest;
import com.general.model.request.FreteRequest;
import com.general.model.request.PesoCubadoRequest;
import com.general.model.response.CustoPorKmResponse;
import com.general.model.response.FreteResponse;
import com.general.model.response.PesoCubadoResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LogisticService {

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
     * @param req FreteRequest
     * @return FreteResponse
     */
    public FreteResponse calcularFrete(FreteRequest req) {
        // Frete por KM rodado
        double freteKm = (req.km() * req.custoPorKm()) + req.taxaCarga();

        // Frete por TONELADA-KM (mais usado para longas distâncias)
        double custoTonKm = req.custoPorKm() / 5.0; // 5ton média
        double freteTonKmTotal = req.toneladas() * req.km() * custoTonKm;

        // Maior valor = frete final (protege transportadora)
        double freteFinal = Math.max(freteKm, freteTonKmTotal);

        // ICMS exemplo SP 12%
        double comIcms = freteFinal * 1.12;

        String metodo = freteTonKmTotal > freteKm ? "Ton-Km" : "KM rodado";
        String rec = String.format("Adicione 12%% ICMS = R$%.2f", comIcms);

        return new FreteResponse(
                freteFinal,
                req.custoPorKm(),
                custoTonKm,
                metodo,
                rec
        );
    }

    public PesoCubadoResponse calcularPesoCubado(PesoCubadoRequest req) {
        // VOLUME em m³: C x L x A
        double volume = req.comprimento() * req.largura() * req.altura();

        // PESO CUBADO = volume × fator (300 rodoviário BR)
        double pesoCubado = volume * req.fatorCubagem();

        // SEMPRE COBRA O MAIOR (truque das transportadoras)
        double pesoFinal = Math.max(req.pesoReal(), pesoCubado);

        double multi = pesoFinal / req.pesoReal();
        String dica = String.format("Cobre %.0fx mais caro! Fatura R$%.2f extra",
                multi, (pesoFinal - req.pesoReal()) * 1.68);

        return new PesoCubadoResponse(volume, pesoCubado, pesoFinal, dica, multi);
    }
}
