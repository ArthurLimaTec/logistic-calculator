package com.general.model.reail.response;

public record VagaoResponse(
        int totalVagões,          // 500
        int numeroTrens,          // 5
        double capacidadeTotal,   // 50.000 ton
        String composicao         // "5 trens × 100 vagões"
) {}