package com.general.model.maritime.response;

public record ThcResponse(
        double valorUnitarioUsd,  // US$180/20'
        double totalThcUsd,       // US$9.000 (50×180)
        double totalReal,         // R$49.500 (câmbio 5.5)
        String breakdown,         // "50×US$180 = US$9k"
        String avisoThc2          // "🚨 THC2 em disputa judicial"
) {}