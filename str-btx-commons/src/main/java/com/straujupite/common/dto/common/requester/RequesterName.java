package com.straujupite.common.dto.common.requester;

import com.straujupite.common.util.SingleFieldObject;
import lombok.Data;

@Data
public class RequesterName implements SingleFieldObject<String> {

    private final String value;
}
