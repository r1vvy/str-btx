package com.straujupite.common.dto.common.sheet;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SheetRowData {

    private String requesterType;

    private String requesterName;

    private String requesterSurname;

    private String orgName;

    private String orgRegistrationNum;

    private String jurAddress;

    private String requesterEmail;

    private String requesterPhone;

    private String deliveryAddress;

    private String postalIndex;

    private Integer cardCount;
}
