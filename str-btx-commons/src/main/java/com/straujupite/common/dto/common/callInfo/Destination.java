package com.straujupite.common.dto.common.callInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Destination {

  @NotBlank
  @JsonProperty("destination")
  private final String value;
}
