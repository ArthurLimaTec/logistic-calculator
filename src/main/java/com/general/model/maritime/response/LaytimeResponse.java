package com.general.model.maritime.response;

public record LaytimeResponse(
        double horasPermitidas,   // 25 horas
        double diasTotais,        // 1.04 dias
        String tempoExplicado,    // "25h ou 1d 1h15m"
        double demurrageRisco,    // "$25k se atrasar 1 dia"
        String tipoUsado          // "WWDSHEX - Santos padrão"
) {}