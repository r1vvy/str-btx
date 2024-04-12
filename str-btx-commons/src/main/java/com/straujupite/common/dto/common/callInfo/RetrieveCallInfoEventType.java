package com.straujupite.common.dto.common.callInfo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;


@Getter
public enum RetrieveCallInfoEventType {

  CALL_COMPLETED("CallCompleted"),
  CALL_STARTED("CallStarted"),
  CALL_CONNECTED("CallConnected"),
  LOST_CALLER_ADDED("LostCallerAdded"),
  LOST_CALLER_UPDATED("LostCallerUpdated"),
  LOST_CALLER_REMOVED("LostCallerRemoved");

  @JsonValue
  private final String value;

  RetrieveCallInfoEventType(String value) {
    this.value = value;
  }

  @JsonCreator
  public static RetrieveCallInfoEventType forValue(String value) {
    for (RetrieveCallInfoEventType type : RetrieveCallInfoEventType.values()) {
      if (type.getValue().equalsIgnoreCase(value)) {
        return type;
      }
    }
    throw new IllegalArgumentException("Unknown RetrieveCallInfoEventType: " + value);
  }
}
