package com.straujupite.common.dto.common;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PhoneNumber {

  @Pattern(regexp = "^\\+?[0-9]+$")
  private final String value;
}
