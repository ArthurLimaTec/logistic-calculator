package com.general.model.air.response;

public record SlotResponse(
        int slotsNecessarios,     // 3 slots GRU
        double capacidadeSlot,    // 10 ton/slot
        String planejamento,      // "3 slots × 2h = 25ton"
        String avisoGru            // "Slots GRU limitados!"
) {}