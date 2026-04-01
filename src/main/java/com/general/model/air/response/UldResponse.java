package com.general.model.air.response;

public record UldResponse(
        int slotsOcupados,        // 160 slots
        double percentAeronave,   // 80% capacidade
        int aeronavesNecessarias, // 2 aviões
        String layout             // "20 pallets = 2 B777F"
) {}