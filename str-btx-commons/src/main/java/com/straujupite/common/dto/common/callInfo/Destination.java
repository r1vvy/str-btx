package com.straujupite.common.dto.common.callInfo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class Destination {

  @NotBlank
  @JsonProperty("destination")
  String number;

  @JsonCreator
  public Destination(@JsonProperty("destination") String number) {
    this.number = number;
  }
}
