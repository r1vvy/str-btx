package com.straujupite.common.dto.common.callInfo;

import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CallInfo {

  @Valid
  private final Caller caller;
  @Valid
  private final Destination destination;

  private final CallDirection direction;

  private final String callStartedDateTime;

  private final String callConnectedDateTime;

  private final String callEndedDateTime;

  private final String lastContactDateTime;

  private final String contactName;

  private final Integer connectionTime;

  private final Integer attempts;

  private final Integer status;
}
