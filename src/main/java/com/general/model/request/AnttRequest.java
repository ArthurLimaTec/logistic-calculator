package com.general.model.request;

public record AnttRequest(
        String tipoCarga,         // "geral", "granel", "frigorificada"
        int eixos,                // 2-9 eixos
        double distanciaKm,       // 400km
        boolean composicao        // true = caminhão+carreta
) {
}
