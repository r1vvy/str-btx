package com.straujupite.core.service.sheets;

import com.straujupite.common.dto.common.AppendSheetCommand;
import com.straujupite.common.dto.result.AppendSheetResult;
import com.straujupite.out.adapter.SheetsOutAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SheetsServiceImpl implements SheetsService {

    private final SheetsOutAdapter sheetsOutAdapter;

    @Override
    public Mono<AppendSheetResult> appendSheet(AppendSheetCommand command) {
        return Mono.justOrEmpty(command)
                   .flatMap(sheetsOutAdapter::append)
                   .map(response -> AppendSheetResult.builder()
                                                     .build());
    }
}
