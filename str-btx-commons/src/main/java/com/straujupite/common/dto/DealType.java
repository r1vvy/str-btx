package com.straujupite.common.dto;

public enum DealType {

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

    private final String dealType;

    DealType(String dealType) {
        this.dealType = dealType;
    }

    public static DealType getDealType(String dealType) {
        for (DealType deal : DealType.values()) {
            if (deal.dealType.equalsIgnoreCase(dealType)) {
                return deal;
            }
        }
        return null;

    }

}
