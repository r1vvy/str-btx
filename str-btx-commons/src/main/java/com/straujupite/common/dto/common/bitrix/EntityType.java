package com.straujupite.common.dto.common.bitrix;

import lombok.Getter;

@Getter
public enum EntityType {

  COMPANY("company");

  private final String entityType;

  EntityType(String entityType) {
    this.entityType = entityType;
  }
}
