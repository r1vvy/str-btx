package com.straujupite.common.dto.common.callInfo;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
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

  private final LocalDateTime callStarted;

  private final LocalDateTime callConnected;

  private final Integer connectionTime;

  private final Integer attempts;
}
