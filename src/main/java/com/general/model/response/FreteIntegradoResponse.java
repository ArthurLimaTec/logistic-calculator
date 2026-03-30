package com.general.model.response;

public record FreteIntegradoResponse(
        double custoPorKm,        // R$1.40 (calculado)
        double pesoCobrar,        // 2.400kg (cubado > real)
        double freteTotal,        // R$1.282 FINAL!
        double icms12,            // R$1.436 com imposto
        String estrategia,        // "Peso cubado 4.8x mais rentável"
        String cotacaoCliente     // "Orçamento: R$1.436 - 10ton SP→RJ"
) {
}
