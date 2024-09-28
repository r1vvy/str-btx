package com.straujupite.out.adapter;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.Sheets.Spreadsheets.Values;
import com.google.api.services.sheets.v4.Sheets.Spreadsheets.Values.Append;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.straujupite.common.converter.ConversionService;
import com.straujupite.common.dto.common.AppendSheetCommand;
import com.straujupite.common.dto.google.ValueInputOption;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;

import static com.straujupite.common.util.ReactorMdcUtil.logOnNext;

@Service
@RequiredArgsConstructor
@Slf4j
public class SheetsOutAdapterImpl implements SheetsOutAdapter {

    @Autowired
    private Sheets sheetsOutClient;

    @Autowired
    private ConversionService conversionService;

    @Override
    public Mono<AppendValuesResponse> append(AppendSheetCommand command) {
        return Mono.fromSupplier(command::getRowData)
                   .flatMap(rowData -> conversionService.convert(rowData, ValueRange.class))
                   .doOnEach(logOnNext(valueRange -> log.debug("About to append these values: {}", valueRange.getValues())))
                   .flatMap(valueRange -> callSheetsClient(command, valueRange));
    }

    private Mono<AppendValuesResponse> callSheetsClient(AppendSheetCommand command, ValueRange range) {
        return Mono.just(sheetsOutClient.spreadsheets().values())
                   .map(values -> buildRequest(command, range, values))
                   .doOnEach(logOnNext(request -> log.debug("About to call append sheet to Sheet ID: {}, Data: {}", request.getSpreadsheetId(), request.getRange())))
                   .flatMap(this::execute);
    }

    private Append buildRequest(AppendSheetCommand command, ValueRange range, Values values) {
        try {
            return values.append(command.getSpreadsheetId(), command.getTableRange(), range)
                         .setValueInputOption(ValueInputOption.RAW.getValue());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Mono<AppendValuesResponse> execute(Append request) {
        return Mono.defer(() -> {
            try {
                return Mono.just(request.execute());
            } catch (IOException e) {
                return Mono.error(new RuntimeException(e));
            }
        });
    }
}
