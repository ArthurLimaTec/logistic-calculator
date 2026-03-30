package com.general.service;

import com.general.model.request.CustoPorKmRequest;
import com.general.model.request.FreteRequest;
import com.general.model.response.CustoPorKmResponse;
import com.general.model.response.FreteResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LogisticService {

    /**
     * Calcula o preço do Frete Total por Km
     * @param request
     * @return custo por km, custo por tonelada e recomendação de cobrança pelo transporte com lucro de 20%
     */
    public CustoPorKmResponse calcularCustoKm(CustoPorKmRequest request) {

        double totalMensal = request.dieselMensal() + request.salario() + request.manutencao() + request.outrosFixos() + request.pedagioMedio();

        double custoPorKm = totalMensal / request.kmMensal();

        double custoTonKm = custoPorKm / 5.0; // Assume caminhão 5ton média

        double lucro20 = custoPorKm * 1.20;
        String rec = String.format("Cobre R$%.2f/km (20%% lucro)", lucro20);

        return new CustoPorKmResponse(custoPorKm, custoTonKm, rec);
    }

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
}
