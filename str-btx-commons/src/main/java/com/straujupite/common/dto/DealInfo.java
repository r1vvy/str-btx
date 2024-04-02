package com.straujupite.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.straujupite.common.util.DealStageDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class DealInfo {

  @JsonProperty("ID")
  private String id;

  @JsonProperty("DATE_CREATE")
  private String dateCreated;

  @JsonDeserialize(using = DealStageDeserializer.class)
  @JsonProperty("STAGE_ID")
  private DealStage stage;
}
