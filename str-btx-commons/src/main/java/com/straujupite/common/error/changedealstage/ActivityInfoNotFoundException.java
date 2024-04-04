package com.straujupite.common.error.changedealstage;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ActivityInfoNotFoundException extends BaseChangeDealStageFlowException {

  private final String errorMessage;

  @Override
  public String getErrorMessage() {
    return errorMessage;
  }
}
