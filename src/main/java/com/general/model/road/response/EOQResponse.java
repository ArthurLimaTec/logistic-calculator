package com.general.model.road.response;

public record EOQResponse(
        double quantidadeIdeal,    // 1.250 velas
        int pedidosAno,           // 10 pedidos
        double tempoEntrePedidos, // 36 dias
        double custoTotalAno,     // R$5.000 (pedido+estoque)
        String dica               // "Compre 1.250 velas a cada 36 dias"
) {
}
