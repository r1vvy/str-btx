package com.straujupite.common.dto.result;

import com.straujupite.common.dto.common.RequestId;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RetrieveCallInfoResult {

  @NotBlank
  private final RequestId requestId;

}
