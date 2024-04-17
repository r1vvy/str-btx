package com.straujupite.common.dto.common;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PhoneNumber {

  @Valid
  @NotBlank
  private String value;
}
