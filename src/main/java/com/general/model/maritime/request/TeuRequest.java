package com.general.model.maritime.request;

public record TeuRequest(
        int qtd20ft,
        int qtd40ft,
        int qtd40high,
        int qtd45ft
) {}
