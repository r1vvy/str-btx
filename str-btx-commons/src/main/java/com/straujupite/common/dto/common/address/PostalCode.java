package com.straujupite.common.dto.common.address;

import com.straujupite.common.util.SingleFieldObject;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PostalCode implements SingleFieldObject<String> {

    @Pattern(regexp = "^LV-\\d{4}$")
    private final String value;

}
