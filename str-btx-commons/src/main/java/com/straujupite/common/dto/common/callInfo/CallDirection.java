package com.straujupite.common.dto.common.callInfo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CallDirection {
  OUT("out"),
  IN("in");

  private final String direction;

  @JsonValue
  public String getDirection() {
    return direction;
  }

}
