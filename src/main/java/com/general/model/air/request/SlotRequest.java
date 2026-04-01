package com.general.model.air.request;

public record SlotRequest(
        double toneladas,
        double taxaTonHora,
        double slotHoras
) {
}
