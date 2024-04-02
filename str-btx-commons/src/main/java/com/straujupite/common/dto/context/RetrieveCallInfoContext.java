package com.straujupite.common.dto.context;

import com.straujupite.common.dto.DealInfo;
import com.straujupite.common.dto.DealStage;
import com.straujupite.common.dto.in.command.RetrieveCallInfoCommand;
import com.straujupite.common.dto.out.response.GetActivityOutResponse;
import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@Builder
@With
public class RetrieveCallInfoContext {
  private final RetrieveCallInfoCommand retrieveCallInfoCommand;

  private final String companyPhoneNumber;

  private final String strNumber;

  private final Integer companyId;

  private final GetActivityOutResponse activityInfo;

  private final DealInfo dealInfo;

  private final DealStage newStage;
}
