package com.general.model.maritime.response;

public record MultaResponse(
        double totalMultas,       // R$1.600 (demurrage + detention)
        double demurrage,         // R$1.500 porto
        double detention,         // R$300 terminal
        String prazoRecomendado,  // "Retire em 5 dias porto + 7 dias terminal"
        String urgencia           // "🚨 URGENTE: R$500/dia após free time"
) {}