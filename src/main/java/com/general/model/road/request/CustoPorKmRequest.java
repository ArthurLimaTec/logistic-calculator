package com.general.model.road.request;

public record CustoPorKmRequest(
        double dieselMensal,      // R$3.000
        double salario,           // R$1.500
        double manutencao,        // R$1.200
        double outrosFixos,       // R$800 IPVA+seguro
        double pedagioMedio,      // R$500
        double kmMensal          // 5.000km
) {

}
