package com.straujupite.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class GetStageIDInResponse {
  @JsonProperty("result")
  private List<GetStageID> result;
}
