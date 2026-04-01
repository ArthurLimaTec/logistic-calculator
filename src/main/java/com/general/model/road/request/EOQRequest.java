package com.general.model.road.request;

public record EOQRequest(
        double demandaAnual,      // 12.000 velas/ano
        double custoPedido,       // R$200 frete+admin
        double custoEstoqueUnid   // R$10/ano por vela
) {
}
