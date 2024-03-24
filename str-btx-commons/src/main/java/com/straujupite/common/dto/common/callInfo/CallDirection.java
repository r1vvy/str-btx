package com.straujupite.common.dto.common.callInfo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum CallDirection {
  OUT("out"),
  IN("in");

  private final String value;

  @JsonValue
  public String getValue() {
    return value;
  }

  CallDirection(String value) {
    this.value = value;
  }
}
