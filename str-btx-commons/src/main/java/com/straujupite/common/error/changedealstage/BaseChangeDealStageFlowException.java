package com.straujupite.common.error.changedealstage;

public class BaseChangeDealStageFlowException extends RuntimeException implements
    ChangeDealStageFlowError {

  @Override
  public String getErrorMessage() {
    return ChangeDealStageFlowError.super.getErrorMessage();
  }
}
