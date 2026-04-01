package com.general.model.maritime.request;

public record DemurrageRequest(
        int diasPorto,
        int diasFreePorto,
        double taxaDemurrage,
        int diasTerminal,
        int diasFreeTerminal,
        double taxaDetention
) {}