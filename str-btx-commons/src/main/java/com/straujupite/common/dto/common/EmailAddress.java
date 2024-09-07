package com.straujupite.common.dto.common;

import com.straujupite.common.util.SingleFieldObject;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class EmailAddress implements SingleFieldObject<String> {

    @Email
    private final String value;
}
