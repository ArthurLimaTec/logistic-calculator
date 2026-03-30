package com.general.model.request;

public record PesoCubadoRequest(
        double pesoReal,          // 500kg reais
        double comprimento,       // 2.0 metros
        double largura,           // 2.0 metros
        double altura,            // 2.0 metros
        int fatorCubagem,         // 300 (rodoviário) ou 167 (aéreo)
        String modal              // "rodoviario", "aereo"
) {
}
