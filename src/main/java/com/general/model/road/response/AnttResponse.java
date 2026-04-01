package com.general.model.road.response;

public record AnttResponse(
        double ccd,               // R$4.509/km eixo
        double cc,                // R$448 fixo
        double freteMinimo,       // R$2.251 oficial
        String multaAviso,        // "NÃO cobre menos que isso!"
        String tabelaUsada        // "Tabela A - Carga Geral"
) {
}
