package com.general.service;

import com.general.model.air.request.FreteAereoRequest;
import com.general.model.air.request.SlotRequest;
import com.general.model.air.request.UldRequest;
import com.general.model.air.request.VolumetricoRequest;
import com.general.model.air.response.FreteAereoResponse;
import com.general.model.air.response.SlotResponse;
import com.general.model.air.response.UldResponse;
import com.general.model.air.response.VolumetricoResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AirService {

    /**
     * Peso Volumétrico Aéreo (Fator 167 kg/m³), frete cobra MAIOR peso real/volumétrico
     * @param req VolumetricoRequest
     * @return VolumetricoResponse
     */
    public VolumetricoResponse calcularVolumetrico(VolumetricoRequest req) {
        double m3 = (req.comprimentoCm() * req.larguraCm() * req.alturaCm()) / 1000000.0;
        double volumetrico = m3 * 167; // IATA padrão
        double cobrar = Math.max(req.pesoReal(), volumetrico);
        double multi = cobrar / req.pesoReal();

        return new VolumetricoResponse(volumetrico, cobrar, multi,
                String.format("R$%.0f (R$10/kg Viracopos)", cobrar * 10));
    }

    /**
     * ULD (Unit Load Device) Slots, paletes/contêineres aéreos Boeing 777F
     * @param req UldRequest
     * @return UldResponse
     */
    public UldResponse calcularUld(UldRequest req) {
        int slotsPorUld = switch(req.tipoUld()) {
            case "Pallet96125" -> 8;
            case "AKE" -> 4;
            default -> 8;
        };
        int slotsTotais = req.quantidade() * slotsPorUld;
        int aeronaves = (int) Math.ceil(slotsTotais / 200.0); // B777F

        return new UldResponse(slotsTotais, (slotsTotais/200.0)*100, aeronaves,
                String.format("%d %s = %d aviões", req.quantidade(), req.tipoUld(), aeronaves));
    }

    /**
     * Frete Aéreo TK (Ton-Km)
     * @param req FreteAereoRequest
     * @return FreteAereoResponse
     */
    public FreteAereoResponse calcularFreteAereo(FreteAereoRequest req) {
        double[] tkRota = {3.50, 4.20, 5.80}; // GRU-America/Europa/Asia
        int idx = switch(req.rota()) {
            case "GRU-MIA" -> 0; case "GRU-FRA" -> 1; default -> 2;
        };
        double custoTk = tkRota[idx];
        double total = req.toneladas() * req.distanciaKm() * custoTk;
        double maritimo = total * 0.07; // 7% marítimo

        return new FreteAereoResponse(total, custoTk, maritimo,
                String.format("%.1ft × %.0fkm × R$%.2f", req.toneladas(), req.distanciaKm(), custoTk));
    }

    /**
     * Slot Aeroporto (Guarulhos), horários limitados GRU cargo
     * @param req SlotRequest
     * @return SlotResponse
     */
    public SlotResponse calcularSlot(SlotRequest req) {
        double capacidade = req.taxaTonHora() * req.slotHoras();
        int slots = (int) Math.ceil(req.toneladas() / capacidade);

        return new SlotResponse(slots, capacidade,
                String.format("%d slots × %.0fh = %.0ft", slots, req.slotHoras(), capacidade),
                "Slots GRU limitados - reserve 30 dias antes!"
        );
    }
}
