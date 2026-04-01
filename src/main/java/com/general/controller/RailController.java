package com.general.controller;

import com.general.model.reail.request.DemurrageFerroRequest;
import com.general.model.reail.request.TkuRequest;
import com.general.model.reail.request.TracaoRequest;
import com.general.model.reail.request.VagaoRequest;
import com.general.model.reail.response.DemurrageFerroResponse;
import com.general.model.reail.response.TkuResponse;
import com.general.model.reail.response.TracaoResponse;
import com.general.model.reail.response.VagaoResponse;
import com.general.service.LogisticService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rail")
@Tag(name = "🚂 Logística Ferroviária")
@RequiredArgsConstructor
public class RailController {

    private final LogisticService service;

    @PostMapping("/tku")
    @Operation(summary = "💰 Custo TKU R$0,12/ton-km")
    public ResponseEntity<TkuResponse> tku(@RequestBody @Valid TkuRequest req) {
        return ResponseEntity.ok(service.calcularTku(req));
    }

    @PostMapping("/vagoes")
    @Operation(summary = "🚂 Nº vagões + trens necessários")
    public ResponseEntity<VagaoResponse> vagoes(@RequestBody @Valid VagaoRequest req) {
        return ResponseEntity.ok(service.calcularVagoes(req));
    }

    @PostMapping("/demurrage-ferro")
    @Operation(summary = "⏰ Demurrage vagões R$2.5k/dia")
    public ResponseEntity<DemurrageFerroResponse> demoFerro(@RequestBody @Valid DemurrageFerroRequest req) {
        return ResponseEntity.ok(service.calcularDemurrageFerro(req));
    }

    @PostMapping("/tracao")
    @Operation(summary = "🚂 Capacidade Tração Locomotiva")
    public ResponseEntity<TracaoResponse> tracao(@RequestBody @Valid TracaoRequest req) {
        return ResponseEntity.ok(service.calcularVagoesTracao(req));
    }

}
