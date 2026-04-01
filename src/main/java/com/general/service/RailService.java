package com.general.service;

import com.general.model.reail.request.DemurrageFerroRequest;
import com.general.model.reail.request.TkuRequest;
import com.general.model.reail.request.TracaoRequest;
import com.general.model.reail.request.VagaoRequest;
import com.general.model.reail.response.DemurrageFerroResponse;
import com.general.model.reail.response.TkuResponse;
import com.general.model.reail.response.TracaoResponse;
import com.general.model.reail.response.VagaoResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RailService {

    /**
     * Custo TKU Ferroviária, Cotação trem vs caminhão
     * @param req TkuRequest
     * @return TkuResponse
     */
    public TkuResponse calcularTku(TkuRequest req) {
        double[] rcfs = {0.12, 0.15, 0.18};
        int idx = switch(req.tipoCarga()) { case "granel" -> 0; case "contêiner" -> 1; default -> 2; };
        double custoTku = rcfs[idx];
        double total = req.toneladas() * req.distanciaKm() * custoTku;
        double caminhao = total * 2.5; // 2.5x mais caro

        return new TkuResponse(total, custoTku, caminhao,
                String.format("%.0ft × %.0fkm × R$%.2f", req.toneladas(), req.distanciaKm(), custoTku));
    }

    /**
     * Nº Vagões por Trem, Dimensiona composição
     * @param req VagaoRequest
     * @return VagaoResponse
     */
    public VagaoResponse calcularVagoes(VagaoRequest req) {
        double tonVagao = "granel".equals(req.tipoVagao()) ? 100 : 25;
        int vagoes = (int) Math.ceil(req.cargaTon() / tonVagao);
        int trens = (int) Math.ceil(vagoes / 120.0);
        double capacidade = vagoes * tonVagao;

        return new VagaoResponse(vagoes, trens, capacidade,
                String.format("%d trens × %.0f vagões", trens, vagoes / (double)trens));
    }

    /**
     * Demurrage Ferroviária (R$5k/dia), multa por atraso
     * @param req DemurrageFerroRequest
     * @return DemurrageFerroResponse
     */
    public DemurrageFerroResponse calcularDemurrageFerro(DemurrageFerroRequest req) {
        int atrasoDias = Math.max(0, (req.horasAtraso() - req.freeHours()) / 24);
        double multa = atrasoDias * 2500.0 +
                ((req.horasAtraso() - req.freeHours()) % 24) * (2500.0 / 24);

        return new DemurrageFerroResponse(multa, atrasoDias, 2500.0,
                String.format("🚨 Free time %dh - Multa R$%.0f/dia", req.freeHours(), 2500.0));
    }

    /**
     * Capacidade de Tração Locomotiva
     * @param req TracaoRequest
     * @return int
     */
    public TracaoResponse calcularVagoesTracao(TracaoRequest req) {
        double tonVagao = "granel".equals(req.tipoCarga()) ? 100 : 25;
        int vagoes = (int) (req.forcaTracaoTon() / tonVagao);
        double cargaMax = vagoes * tonVagao;

        return new TracaoResponse(vagoes, req.forcaTracaoTon(), cargaMax,
                String.format("Loco %.0fHP traciona %d vagões", req.forcaTracaoTon()/0.75, vagoes));
    }
}
