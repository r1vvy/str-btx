package com.straujupite.common.dto.out.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateActivityOutResponse {

  @JsonProperty("id")
  private String resultId;
}
