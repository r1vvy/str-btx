package com.straujupite.common.dto.common;

import com.straujupite.common.dto.common.sheet.SheetRowData;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppendSheetCommand {

    private String spreadsheetId;

    private String tableRange;

    private SheetRowData rowData;
}
