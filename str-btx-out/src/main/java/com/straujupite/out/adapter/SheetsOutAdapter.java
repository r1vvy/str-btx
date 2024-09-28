package com.straujupite.out.adapter;

import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.straujupite.common.dto.common.AppendSheetCommand;
import reactor.core.publisher.Mono;

public interface SheetsOutAdapter {
    Mono<AppendValuesResponse> append(AppendSheetCommand command);
}
