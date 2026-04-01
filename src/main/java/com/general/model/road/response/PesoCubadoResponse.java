package com.general.model.road.response;

public record PesoCubadoResponse(
        double volumeM3,          // 8.0 m³
        double pesoCubado,        // 2400kg
        double pesoCobrar,        // 2400kg (MAIOR valor)
        String dica,              // "Cobre 4,8x mais caro!"
        double multiplicador      // 4.8x
) {
}
