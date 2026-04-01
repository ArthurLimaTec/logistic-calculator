package com.general.model.reail.request;

public record DemurrageFerroRequest(
        int horasAtraso,          // 96h
        int freeHours             // 72h
) {}