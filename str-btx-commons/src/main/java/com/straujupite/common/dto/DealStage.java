package com.straujupite.common.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DealStage {

    NEW_DEAL("NEW"),
    IN_PROCESS("UC_VL6HOT"),
    DIDNT_PICKUP("PREPARATION"),
    DIDNT_PICKUP_AND_SMS("PREPAYMENT_INVOICE"),
    DIDNT_PICKUP_AND_EMAIL("EXECUTING"),
    RECALL("FINAL_INVOICE"),
    WAITING_FOR_WINTER_DIESEL("1"),
    SEARCH_FOR_NEW_NUMBER("2"),
    SUCCESSFUL_DEAL("WON"),
    FAILED_DEAL("LOSE");

  private final String value;

    DealStage(String stage) {
      this.value = stage;
    }

  @JsonCreator
  public static DealStage fromValue(String value) {
    for (DealStage type : DealStage.values()) {
      if (type.getValue().equalsIgnoreCase(value)) {
        return type;
            }
        }
    throw new IllegalArgumentException("Unknown DealStage: " + value);
  }

  @JsonValue
  public String getValue() {
    return value;
    }

}
