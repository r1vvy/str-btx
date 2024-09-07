package com.straujupite.core.service.sheets;


import com.straujupite.common.dto.common.AppendSheetCommand;
import com.straujupite.common.dto.result.AppendSheetResult;
import reactor.core.publisher.Mono;

public interface SheetsService {

    Mono<AppendSheetResult> appendSheet(AppendSheetCommand command);
}
