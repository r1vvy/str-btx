package com.straujupite.common.dto.result;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppendSheetResult {
    private final String spreadSheetId;
    private final String tableRange;
}
