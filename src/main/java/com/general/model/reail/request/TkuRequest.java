package com.general.model.reail.request;

public record TkuRequest(
        double toneladas,         // 50.000 ton soja
        double distanciaKm,       // 1.000km Rumo S.A.
        String tipoCarga          // "granel", "contêiner", "carga fechada"
) {}