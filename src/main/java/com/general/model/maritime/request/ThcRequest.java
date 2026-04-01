package com.general.model.maritime.request;

public record ThcRequest(
        String tipoContainer,     // "20seco", "40seco", "20reefer", "40reefer"
        String porto,             // "Santos", "Paranagua", "Itajai"
        int quantidade,           // 50 contêineres
        boolean thc2              // THC2 polêmica (judicial)
) {}