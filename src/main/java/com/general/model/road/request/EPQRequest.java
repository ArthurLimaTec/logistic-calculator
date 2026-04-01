package com.general.model.road.request;

public record EPQRequest(
        double demandaAnual,      // 60.000 pares/ano
        double custoSetup,        // R$500 setup máquina
        double custoEstoqueUnid,  // R$15/par/ano
        double taxaProducaoAnual  // 250.000 pares/ano (máquina)
) {
}
