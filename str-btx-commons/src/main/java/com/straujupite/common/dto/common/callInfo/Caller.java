package com.straujupite.common.dto.common.callInfo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Caller {

  @NotBlank
  private final String value;
}
