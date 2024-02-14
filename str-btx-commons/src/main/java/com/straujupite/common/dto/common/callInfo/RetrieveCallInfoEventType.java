package com.straujupite.common.dto.common.callInfo;

import lombok.Getter;


@Getter
public enum RetrieveCallInfoEventType {

  CALL_COMPLETED("CallCompleted"),
  CALL_STARTED("CallStarted"),
  CALL_CONNECTED("CallConnected"),
  LOST_CALLER_ADDED("LostCallerAdded"),
  LOST_CALLER_UPDATED("LostCallerUpdated"),
  LOST_CALLER_REMOVED("LostCallerRemoved");

  private final String eventType;

  RetrieveCallInfoEventType(String eventType) {
    this.eventType = eventType;
  }
}
