package com.general.model.air.request;

public record FreteAereoRequest(
        double toneladas,
        double distanciaKm,
        String rota
) {
}
