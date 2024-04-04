package com.straujupite.common.error.changedealstage;

public interface ChangeDealStageFlowError {

  default String getErrorMessage() {
    return "An error occured on change deal stage flow";
  }
}
