package com.straujupite.common.dto.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ApiVersion {

  @NotBlank
  private final String version;

  @JsonCreator
  public ApiVersion(@JsonProperty("version") String version) {
    this.version = version;
  }
}