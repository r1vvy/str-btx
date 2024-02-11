package com.straujupite.common.dto.common;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ApiVersion {

  @NotBlank
  private final String version;
}
