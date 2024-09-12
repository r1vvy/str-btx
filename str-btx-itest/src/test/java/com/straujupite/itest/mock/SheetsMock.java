package com.straujupite.itest.mock;

import org.springframework.stereotype.Component;

@Component
public class SheetsMock extends BaseMock {

    public void stubAddDataToSheet(String requestFileName, String responseFileName) {
        stubPost("/v4/spreadsheets/spreadsheet-id/values/table-range:append?valueInputOption=RAW",
                200,
                OUTGOING_JSON_PATH + "/appendSheet/" + requestFileName,
                OUTGOING_JSON_PATH + "/appendSheet/" + responseFileName
        );
    }
}
