package com.general.service;

import com.general.model.air.request.FreteAereoRequest;
import com.general.model.air.request.SlotRequest;
import com.general.model.air.request.UldRequest;
import com.general.model.air.request.VolumetricoRequest;
import com.general.model.air.response.FreteAereoResponse;
import com.general.model.air.response.SlotResponse;
import com.general.model.air.response.UldResponse;
import com.general.model.air.response.VolumetricoResponse;
import com.general.model.maritime.request.*;
import com.general.model.maritime.response.*;
import com.general.model.reail.request.DemurrageFerroRequest;
import com.general.model.reail.request.TkuRequest;
import com.general.model.reail.request.TracaoRequest;
import com.general.model.reail.request.VagaoRequest;
import com.general.model.reail.response.DemurrageFerroResponse;
import com.general.model.reail.response.TkuResponse;
import com.general.model.reail.response.TracaoResponse;
import com.general.model.reail.response.VagaoResponse;
import com.general.model.road.request.*;
import com.general.model.road.response.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LogisticService {

    private final RoadService roadService;
    private final MaritimeService maritimeService;
    private final RailService railService;
    private final AirService airService;

    public CustoPorKmResponse calcularCustoKm(CustoPorKmRequest request) {return roadService.calcularCustoKm(request);}

    public FreteResponse calcularFrete(FreteRequest request) {
        return roadService.calcularFrete(request);
    }

    public PesoCubadoResponse calcularPesoCubado(PesoCubadoRequest request) {return roadService.calcularPesoCubado(request);}

    public FreteIntegradoResponse calcularFreteIntegrado(FreteIntegradoRequest req) {return roadService.calcularFreteIntegrado(req);}

    public EOQResponse calcularEOQ(EOQRequest req) {
        return roadService.calcularEOQ(req);
    }

    public AnttResponse calcularAntt(AnttRequest req) {
        return roadService.calcularAntt(req);
    }

    public EPQResponse calcularEPQ(EPQRequest req) {
        return roadService.calcularEPQ(req);
    }

    public double calcularTeu(TeuRequest req) {
        return maritimeService.calcularTeu(req);
    }

    public double calcularBaf(BafRequest req) {
        return maritimeService.calcularBaf(req);
    }

    public MultaResponse calcularDemurrage(DemurrageRequest req) {
        return maritimeService.calcularDemurrage(req);
    }

    public DeslocamentoResponse calcularDeslocamento(DeslocamentoRequest req) {return maritimeService.calcularDeslocamento(req);}

    public IncotermsResponse calcularIncoterms(IncotermsRequest req) {
        return maritimeService.calcularIncoterms(req);
    }

    public LaytimeResponse calcularLaytime(LaytimeRequest req) {
        return maritimeService.calcularLaytime(req);
    }

    public ThcResponse calcularThc(ThcRequest req) {
        return maritimeService.calcularThc(req);
    }

    public TkuResponse calcularTku(TkuRequest req) {return railService.calcularTku(req);}

    public VagaoResponse calcularVagoes(VagaoRequest req) {return railService.calcularVagoes(req);}

    public DemurrageFerroResponse calcularDemurrageFerro(DemurrageFerroRequest req) {return railService.calcularDemurrageFerro(req);}

    public TracaoResponse calcularVagoesTracao(TracaoRequest req) {return railService.calcularVagoesTracao(req);}

    public VolumetricoResponse calcularVolumetrico(VolumetricoRequest req) {return airService.calcularVolumetrico(req);}

    public UldResponse calcularUld(UldRequest req) {return airService.calcularUld(req);}

    public FreteAereoResponse calcularFreteAereo(FreteAereoRequest req) {return airService.calcularFreteAereo(req);}

    public SlotResponse calcularSlot(SlotRequest req) {return airService.calcularSlot(req);}
}
