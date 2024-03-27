package com.straujupite.common.dto.context;

import com.straujupite.common.dto.GetStageID;
import com.straujupite.common.dto.in.command.RetrieveCallInfoCommand;
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

  private final GetStageID dealStage;

}
