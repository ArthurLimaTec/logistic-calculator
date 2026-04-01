package com.general.controller;

import com.general.model.maritime.request.*;
import com.general.model.maritime.response.*;
import com.general.service.LogisticService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/maritime")
@Tag(name = "🌊 Logística Marítima")
@RequiredArgsConstructor
public class MaritimeController {

    private final LogisticService service;

    @PostMapping("/teu")
    @Operation(summary = "📦 Calcula o TEU total da carga")
    public ResponseEntity<Double> calcularTeu(@RequestBody @Valid TeuRequest req) {
        return ResponseEntity.ok(service.calcularTeu(req));
    }

    @PostMapping("/baf")
    @Operation(summary = "⛽ BAF - Sobretaxa combustível do navio")
    public ResponseEntity<Double> calcularBaf(@RequestBody @Valid BafRequest req) {
        return ResponseEntity.ok(service.calcularBaf(req));
    }

    @PostMapping("/demurrage")
    @Operation(summary = "⏰ Demurrage/Detention multas")
    public ResponseEntity<MultaResponse> demurrage(@RequestBody @Valid DemurrageRequest req) {
        return ResponseEntity.ok(service.calcularDemurrage(req));
    }

    @Operation(summary = "⚓ Deslocamento navio + estabilidade")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "✅ Calado OK"),
            @ApiResponse(responseCode = "400", description = "❌ Sobrecarga >13.5m")
    })
    @PostMapping("/deslocamento")
    public ResponseEntity<DeslocamentoResponse> deslocamento(@RequestBody @Valid DeslocamentoRequest req) {
        return ResponseEntity.ok(service.calcularDeslocamento(req));
    }

    @Operation(summary = "📋 Incoterms 2020 - Quem paga o quê?")
    @PostMapping("/incoterms")
    public ResponseEntity<IncotermsResponse> incoterms(@RequestBody @Valid IncotermsRequest req) {
        return ResponseEntity.ok(service.calcularIncoterms(req));
    }

    @Operation(summary = "⏰ Laytime - Evite $25k Demurrage!")
    @PostMapping("/laytime")
    public ResponseEntity<LaytimeResponse> laytime(@RequestBody @Valid LaytimeRequest req) {
        return ResponseEntity.ok(service.calcularLaytime(req));
    }

    @Operation(summary = "💰 THC Santos - US$180/20' contêiner!")
    @PostMapping("/thc")
    public ResponseEntity<ThcResponse> thc(@RequestBody @Valid ThcRequest req) {
        return ResponseEntity.ok(service.calcularThc(req));
    }
}