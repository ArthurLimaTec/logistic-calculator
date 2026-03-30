package com.general.model.response;

public record CustoPorKmResponse(
        double custoPorKm,        // R$1.40
        double custoPorTonKm,        // R$0.28 (dividido por 5ton)
        String recomendacao       // "Cobre R$1.68/km (20% lucro)"
) {
}
