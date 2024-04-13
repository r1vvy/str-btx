package com.straujupite.common.dto.common.callInfo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CallInfo {

  @Valid
  @JsonAlias({"callerid", "caller"})
  private Caller caller;

  @Valid
  private Destination destination;

  @Valid
  private CallDirection direction;

  @JsonProperty("callStarted")
  private String callStartedDateTime;

  @JsonProperty("callConnected")
  private String callConnectedDateTime;

  @JsonProperty("callEnded")
  private String callEndedDateTime;

  @JsonProperty("last_contact")
  private String lastContactDateTime;

  @JsonProperty("contact_name")
  private String contactName;

  private Integer connectionTime;

  private Integer status;
}
