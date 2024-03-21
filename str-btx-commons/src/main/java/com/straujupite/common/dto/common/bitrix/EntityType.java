package com.straujupite.common.dto.common.bitrix;

import lombok.Getter;

@Getter
public enum EntityType {

  COMPANY("company"),
  LEAD("lead"),
  DEAL("deal"),
  ORDER("order");

  private final String entityType;

  EntityType(String entityType) {
    this.entityType = entityType;
  }
}
