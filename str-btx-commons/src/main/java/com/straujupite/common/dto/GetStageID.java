package com.straujupite.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.straujupite.common.util.DealTypeDeserializer;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class GetStageID {

  @JsonProperty("ID")
  private String id;

  @JsonProperty("DATE_CREATE")
  private String datecreate;

  @JsonDeserialize(using = DealTypeDeserializer.class)
  @JsonProperty("STAGE_ID")
  private DealType stageid;
}
