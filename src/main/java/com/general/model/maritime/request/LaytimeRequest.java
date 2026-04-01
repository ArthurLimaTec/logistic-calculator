package com.general.model.maritime.request;

public record LaytimeRequest(
        double tonelagem,         // 50.000 ton
        double taxaTonHora,       // 2.000 ton/h
        String tipoLaytime,       // "WWDSHEX", "RUNNING"
        int diasFree              // 5 dias
) {}