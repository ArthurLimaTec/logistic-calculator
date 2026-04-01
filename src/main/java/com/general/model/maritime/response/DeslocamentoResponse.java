package com.general.model.maritime.response;

public record DeslocamentoResponse(
        double deslocamentoTotal,  // 45.200 ton (navio + tudo)
        double deslocamentoVazio, // 25.000 ton casco
        double cargaUtil,         // 18.500 ton carga
        double bunkerAgua,        // 1.700 ton combustível+água
        String estabilidade,      // "OK - Calado 12.2m"
        String avisoCalado        // "Máx 13.5m Santos"
) {}