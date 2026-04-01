package com.general.model.reail.response;

public record DemurrageFerroResponse(
        double multaTotal,        // R$5.833
        int diasAtraso,           // 1 dia
        double porDiaHora,        // R$2.500/dia
        String aviso              // "🚨 Retire em 72h free time!"
) {}