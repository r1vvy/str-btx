package com.straujupite.common.dto.out.request;

import com.straujupite.common.dto.DealStage;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangeDealStageOutRequest {

  private final String dealId;
  private final DealStage dealStage;
}
