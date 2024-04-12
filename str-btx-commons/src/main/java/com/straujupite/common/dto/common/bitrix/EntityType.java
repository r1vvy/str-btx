package com.straujupite.common.dto.common.bitrix;

import lombok.Getter;

@Getter
public enum EntityType {

  COMPANY("company"),
  LEAD("lead"),
  DEAL("deal"),
  ORDER("order");

  private final String value;

  EntityType(String value) {
    this.value = value;
  }
}
