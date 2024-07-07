package com.straujupite.common.dto.out.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.straujupite.common.dto.DealInfo;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCompanyDealsOutResponse {

  @Valid
  @JsonProperty("result")
  private List<DealInfo> deals;
}
