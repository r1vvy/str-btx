package com.straujupite.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DealInfo {

  @JsonProperty("ID")
  private String id;

  @JsonProperty("DATE_CREATE")
  private String dateCreated;

  @JsonProperty("STAGE_ID")
  private DealStage stage;
}
