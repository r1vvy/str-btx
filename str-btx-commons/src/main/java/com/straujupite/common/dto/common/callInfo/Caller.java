package com.straujupite.common.dto.common.callInfo;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Caller {

  @JsonAlias({"callerid", "caller"})
  @NotBlank
  private final String value;
}
