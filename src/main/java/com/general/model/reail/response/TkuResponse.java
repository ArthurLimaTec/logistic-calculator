package com.general.model.reail.response;

public record TkuResponse(
        double custoTotalReal,    // R$6.000.000
        double custoPorTonKm,     // R$0.12
        double comparativoCaminhao, // R$15mi (+150%)
        String breakdown          // "50k ton × 1k km × R$0,12"
) {}