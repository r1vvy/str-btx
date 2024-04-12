package com.straujupite.common.dto.common.callInfo;

import com.fasterxml.jackson.annotation.JsonCreator;
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

  @JsonCreator
  public static CallDirection forValue(String value) {
    for (CallDirection type : CallDirection.values()) {
      if (type.getValue().equalsIgnoreCase(value)) {
        return type;
      }
    }
    throw new IllegalArgumentException("Unknown CallDirection: " + value);
  }
}
