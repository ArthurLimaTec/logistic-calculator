package com.general.model.air.response;

public record VolumetricoResponse(
        double pesoVolumetrico,   // 167kg
        double pesoCobrar,        // 167kg (maior)
        double multiplica,        // 3.34x mais caro!
        String freteEstimado      // "R$1.670 (R$10/kg)"
) {}