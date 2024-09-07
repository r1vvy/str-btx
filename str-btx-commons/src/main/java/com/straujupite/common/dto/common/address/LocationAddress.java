package com.straujupite.common.dto.common.address;

import com.straujupite.common.util.SingleFieldObject;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class LocationAddress implements SingleFieldObject<String> {

    @Length(max = 100)
    private final String value;
}
