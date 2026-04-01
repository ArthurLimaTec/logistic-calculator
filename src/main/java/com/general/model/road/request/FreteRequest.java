package com.general.model.road.request;

public record FreteRequest(
        double toneladas,
        double km,
        double custoPorKm,  // Do cálculo anterior
        String origem,      // "SP"
        String destino,     // "RJ"
        double taxaCarga    // R$250
) {
}
