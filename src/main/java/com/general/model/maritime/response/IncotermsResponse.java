package com.general.model.maritime.response;

public record IncotermsResponse(
        String vendedorPaga,      // "Frete + Seguro"
        String compradorPaga,     // "Desembaraço + II + IPI"
        double totalVendedor,     // $54.500
        double totalComprador,    // $10.000 (impostos estimado)
        String riscoTransferido,  // "Porto Santos (FOB)"
        String recomendacao       // "FOB melhor pro exportador BR"
) {}