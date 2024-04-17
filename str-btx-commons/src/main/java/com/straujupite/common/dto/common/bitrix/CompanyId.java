package com.straujupite.common.dto.common.bitrix;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompanyId {

  @NotBlank
  private String value;

}
