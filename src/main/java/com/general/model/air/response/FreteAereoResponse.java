package com.general.model.air.response;

public record FreteAereoResponse(
        double custoTotalReal,    // R$26.250
        double custoPorTonKm,     // R$3.50
        double comparativoMaritimo, // R$1.800 (-93%)
        String breakdown          // "2.5t × 7.5k km × R$3.50"
) {}