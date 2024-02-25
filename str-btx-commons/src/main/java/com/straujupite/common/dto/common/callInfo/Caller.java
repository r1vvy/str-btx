package com.straujupite.common.dto.common.callInfo;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Caller {

  @JsonAlias({"callerid", "caller"})
  @NotBlank
  private final String number;
}
