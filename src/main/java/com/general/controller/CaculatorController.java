package com.general.controller;

import com.general.model.request.CustoPorKmRequest;
import com.general.model.request.FreteRequest;
import com.general.model.response.CustoPorKmResponse;
import com.general.model.response.FreteResponse;
import com.general.service.LogisticService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calculator")
@RequiredArgsConstructor
public class CaculatorController {

    private final LogisticService logisticService;

    @PostMapping("/custo-km")
    public ResponseEntity<CustoPorKmResponse> custoKm(@RequestBody @Valid CustoPorKmRequest request) {
        return ResponseEntity.ok(logisticService.calcularCustoKm(request));
    }

    @PostMapping("/frete-completo")
    public ResponseEntity<FreteResponse> freteCompleto(@RequestBody @Valid FreteRequest req) {
        return ResponseEntity.ok(logisticService.calcularFrete(req));
    }
}
