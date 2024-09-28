package com.straujupite.common.dto.common.discountcard;

import com.straujupite.common.util.SingleFieldObject;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CardCount implements SingleFieldObject<Integer> {

    @Positive
    private final Integer value;
}
