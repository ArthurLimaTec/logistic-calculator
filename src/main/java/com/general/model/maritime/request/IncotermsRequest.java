package com.general.model.maritime.request;

public record IncotermsRequest(
        String incoterm,          // "FOB", "CIF", "EXW", "DAP"
        String origem, String destino,  // "Santos", "Singapura"
        double valorFobUsd,       // $50.000 carga
        double freteMaritimoUsd,  // $3.000
        double seguroUsd          // $1.500
) {}