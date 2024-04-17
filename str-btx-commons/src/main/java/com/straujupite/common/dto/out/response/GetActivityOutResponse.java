package com.straujupite.common.dto.out.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.straujupite.common.util.deserializer.GetActivityOutResponseDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonDeserialize(using = GetActivityOutResponseDeserializer.class)
public class GetActivityOutResponse {

  @JsonProperty("ID")
  private String activityId;
  @JsonProperty("DEADLINE")
  private String deadLine;
}
