package com.straujupite.common.dto.common.callInfo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Value;

@Value
public class Destination {

  @Getter(onMethod_ = {@JsonProperty("destination")})
  @NotBlank
  String number;
  @JsonCreator
  public Destination(@JsonProperty("destination") String number) {
    this.number = number;
  }

}
