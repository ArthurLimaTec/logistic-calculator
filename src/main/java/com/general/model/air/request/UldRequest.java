package com.general.model.air.request;

public record UldRequest(
        String tipoUld,
        int quantidade,
        String aeronave
) {
}
