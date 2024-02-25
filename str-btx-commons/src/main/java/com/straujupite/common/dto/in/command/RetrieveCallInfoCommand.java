package com.straujupite.common.dto.in.command;

import com.straujupite.common.dto.common.ApiVersion;
import com.straujupite.common.dto.common.StrRequest;
import com.straujupite.common.dto.common.callInfo.CallInfo;
import com.straujupite.common.dto.common.callInfo.RetrieveCallInfoEventType;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class RetrieveCallInfoCommand extends StrRequest {

  @Valid
  private final RetrieveCallInfoEventType eventType;

  @Valid
  private final ApiVersion version;

  @Valid
  private final CallInfo callInfo;
}
