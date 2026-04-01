package com.general.controller;

import com.general.model.air.request.FreteAereoRequest;
import com.general.model.air.request.SlotRequest;
import com.general.model.air.request.UldRequest;
import com.general.model.air.request.VolumetricoRequest;
import com.general.model.air.response.FreteAereoResponse;
import com.general.model.air.response.SlotResponse;
import com.general.model.air.response.UldResponse;
import com.general.model.air.response.VolumetricoResponse;
import com.general.service.LogisticService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/air")
@Tag(name = "✈️ Logística Aérea")
@RequiredArgsConstructor
public class AirController {

    private final LogisticService service;

    @PostMapping("/volumetrico")
    public ResponseEntity<VolumetricoResponse> volumetrico(@RequestBody @Valid VolumetricoRequest req) {
        return ResponseEntity.ok(service.calcularVolumetrico(req));
    }

    @PostMapping("/uld")
    public ResponseEntity<UldResponse> uld(@RequestBody @Valid UldRequest req) {
        return ResponseEntity.ok(service.calcularUld(req));
    }

    @PostMapping("/frete-tk")
    public ResponseEntity<FreteAereoResponse> freteTk(@RequestBody @Valid FreteAereoRequest req) {
        return ResponseEntity.ok(service.calcularFreteAereo(req));
    }

    @PostMapping("/slot")
    public ResponseEntity<SlotResponse> slot(@RequestBody @Valid SlotRequest req) {
        return ResponseEntity.ok(service.calcularSlot(req));
    }
}
