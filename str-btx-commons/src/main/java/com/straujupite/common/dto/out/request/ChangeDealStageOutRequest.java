package com.straujupite.common.dto.out.request;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.straujupite.common.dto.DealStage;
import com.straujupite.common.util.serializer.ChangeDealStageOutSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonSerialize(using = ChangeDealStageOutSerializer.class)
public class ChangeDealStageOutRequest {

  private String dealId;

  private DealStage dealStage;
}
