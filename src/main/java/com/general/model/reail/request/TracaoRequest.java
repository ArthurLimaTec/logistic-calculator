package com.general.model.reail.request;

public record TracaoRequest(
        double forcaTracaoTon,    // 3.000 ton
        String tipoCarga          // "granel"
) {}