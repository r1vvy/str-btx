package com.straujupite.common.dto.out.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GetActivityOutResponse {

  @JsonProperty("ID")
  private final String activityId;
  @JsonProperty("DEADLINE")
  private final String deadLine;
}
