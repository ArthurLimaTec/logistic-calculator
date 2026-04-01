package com.general.model.road.response;

public record EPQResponse(
        double loteIdeal,          // 2.800 pares
        int lotesAno,             // 21 lotes
        double diasEntreLotes,    // 17 dias
        double estoqueMaximo,     // 1.680 pares (64% capacidade)
        double custoTotalAno,     // R$12.600
        String dica               // "Produza 2.800 pares a cada 17 dias"
) {
}
