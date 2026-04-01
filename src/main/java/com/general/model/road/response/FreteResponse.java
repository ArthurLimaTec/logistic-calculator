package com.general.model.road.response;

public record FreteResponse(
        double freteTotal,        // R$1.005 (valor final pro cliente)
        double fretePorKm,        // R$1.68/km
        double fretePorTonPorKm,        // R$0.84/ton-km
        String metodoUsado,       // "Ton-Km mais vantajoso"
        String recomendacao       // "Adicione 12% ICMS = R$1.125 final"
) {
}
