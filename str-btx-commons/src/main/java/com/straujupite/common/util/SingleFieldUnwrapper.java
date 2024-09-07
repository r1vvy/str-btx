package com.straujupite.common.util;

import java.util.Optional;

public class SingleFieldUnwrapper {

    public static <T> T unwrap(SingleFieldObject<T> singleFieldObject) {
        return Optional.ofNullable(singleFieldObject)
                       .map(SingleFieldObject::getValue)
                       .orElse(null);
    }
}
