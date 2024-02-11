package com.straujupite.common.dto.common.callInfo;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RetrieveCallInfoEventType {

  CALL_COMPLETED("CallCompleted"),
  CALL_STARTED("CallStarted"),
  CALL_CONNECTED("CallConnected"),
  LOST_CALLER_ADDED("LostCallerAdded"),
  LOST_CALLER_UPDATED("LostCallerUpdated"),
  LOST_CALLER_REMOVED("LostCallerRemoved");

  @NotBlank
  private final String eventType;

}
