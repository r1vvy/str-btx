package com.straujupite.common.dto.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ApiVersion {

  @NotBlank
  private String version;

  @JsonCreator
  public ApiVersion(String version) {
    this.version = version;
  }
}