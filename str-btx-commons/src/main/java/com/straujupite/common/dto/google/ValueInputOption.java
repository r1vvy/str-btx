package com.straujupite.common.dto.google;

import lombok.Getter;

@Getter
public enum ValueInputOption {
    RAW("RAW");

    private final String value;

    ValueInputOption(String value) {
        this.value = value;
    }
}
