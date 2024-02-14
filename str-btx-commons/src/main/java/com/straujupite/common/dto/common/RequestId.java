package com.straujupite.common.dto.common;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestId {

  @NotBlank
  private final String requestId;

}
