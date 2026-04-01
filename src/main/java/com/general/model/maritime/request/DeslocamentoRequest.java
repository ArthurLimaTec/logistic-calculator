package com.general.model.maritime.request;

public record DeslocamentoRequest(
        double pesoCascoVazio,    // 25.000 ton
        double bunkerTon,         // 1.200 ton combustível
        double aguaBalastTon,     // 500 ton água
        double cargaTon           // 18.500 ton contêineres
) {}