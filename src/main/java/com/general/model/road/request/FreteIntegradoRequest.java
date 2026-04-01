package com.general.model.road.request;

public record FreteIntegradoRequest(
        double pesoReal,          // 500kg
        double comp, double larg, double alt,  // 2x2x2m
        double km,                // 450km SP-RJ
        double dieselMensal, double salario, double manutencao,  // Para custo/km
        double kmMensal,          // 5.000km/mês
        double taxaCarga          // R$250
) {}