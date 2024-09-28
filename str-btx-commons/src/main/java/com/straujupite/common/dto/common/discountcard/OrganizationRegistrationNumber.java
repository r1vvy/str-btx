package com.straujupite.common.dto.common.discountcard;

import com.straujupite.common.util.SingleFieldObject;
import lombok.Data;

@Data
public class OrganizationRegistrationNumber implements SingleFieldObject<String> {

    private final String value;
}
