package com.straujupite.common.dto.common;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RequestId {

  @NotBlank
  private String value;

}
