package com.straujupite.common.dto.context;

import com.straujupite.common.dto.DealInfo;
import com.straujupite.common.dto.DealStage;
import com.straujupite.common.dto.common.callInfo.RetrieveCallInfoEventType;
import com.straujupite.common.dto.out.response.GetActivityOutResponse;
import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@Builder
@With
public class ChangeDealStageContext {

  RetrieveCallInfoEventType eventType;
  DealInfo dealInfo;
  DealStage updatedDealStage;
  GetActivityOutResponse activityInfo;
}
