package com.straujupite.common.dto.in.command;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.straujupite.common.dto.common.ApiVersion;
import com.straujupite.common.dto.common.StrRequest;
import com.straujupite.common.dto.common.callInfo.CallInfo;
import com.straujupite.common.dto.common.callInfo.RetrieveCallInfoEventType;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RetrieveCallInfoCommand extends StrRequest {

  @Valid
  private RetrieveCallInfoEventType eventType;

  @Valid
  private ApiVersion version;

  @Valid
  private CallInfo callInfo;

  @JsonCreator
  public RetrieveCallInfoCommand(
      @JsonProperty("eventType") RetrieveCallInfoEventType eventType,
      @JsonProperty("version") ApiVersion version,
      @JsonProperty("data") CallInfo callInfo) {
    this.eventType = eventType;
    this.version = version;
    this.callInfo = callInfo;
  }
}
