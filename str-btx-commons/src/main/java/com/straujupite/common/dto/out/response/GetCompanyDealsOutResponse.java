package com.straujupite.common.dto.out.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.straujupite.common.dto.DealInfo;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class GetCompanyDealsOutResponse {

  @JsonProperty("result")
  private List<DealInfo> deals;
}
