package com.general.model.reail.response;

public record TracaoResponse(
        int vagoesMaximos,        // 30 vagões
        double tracaoDisponivel,  // 3.000 ton
        double cargaMaxima,       // 3.000 ton
        String locoRecomendada    // "GE Dash9 - 4.000HP"
) {}