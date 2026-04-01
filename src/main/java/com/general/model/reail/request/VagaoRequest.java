package com.general.model.reail.request;

public record VagaoRequest(
        double cargaTon,          // 50.000 ton
        String tipoVagao          // "granel", "contêiner"
) {}