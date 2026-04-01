package com.general.model.maritime.request;

public record BafRequest(
        double distanciaNm,       // 5.000 milhas náuticas Santos-Roterdã
        double precoBunkerUsd,    // $800/ton (Petrobras API)
        double consumoPorMilha,   // 0.5 ton/milha (navio médio)
        double teuCarga           // 1.500 TEU
) {}
