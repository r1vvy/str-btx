package com.straujupite.core.service.addiscountcard;

import com.straujupite.common.dto.common.AppendSheetCommand;
import com.straujupite.common.dto.common.sheet.SheetRowData;
import com.straujupite.common.dto.in.command.AddDiscountCardCommand;
import com.straujupite.common.dto.result.AddDiscountCardResult;
import com.straujupite.core.service.sheets.SheetsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.straujupite.common.util.SingleFieldUnwrapper.unwrap;

@Service
@RequiredArgsConstructor
public class AddDiscountCardServiceImpl implements AddDiscountCardService {

    @Value("${straujupite.google.integration.spreadsheet-id}")
    private String spreadsheetId;
    @Value("${straujupite.google.integration.table-range}")
    private String tableRange;

    private final SheetsService sheetsService;

    @Override
    public Mono<AddDiscountCardResult> addDiscountCard(AddDiscountCardCommand command) {
        return Mono.justOrEmpty(command)
                   .map(this::buildRowData)
                   .map(this::buildAppendSheetCommand)
                   .flatMap(sheetsService::appendSheet)
                   .map(response -> AddDiscountCardResult.builder()
                                                         .build());
    }

    private SheetRowData buildRowData(AddDiscountCardCommand command) {
        return SheetRowData.builder()
                           .cardCount(unwrap(command.getCardCount()))
                           .deliveryAddress(command.getDeliveryAddress().getValue())
                           .jurAddress(unwrap(command.getLegalAddress()))
                           .orgName(unwrap(command.getOrganizationName()))
                           .orgRegistrationNum(unwrap(command.getOrganizationRegistrationNumber()))
                           .postalIndex(command.getPostalCode().getValue())
                           .requesterEmail(command.getRequesterEmail().getValue())
                           .requesterPhone(command.getRequesterPhone().getValue())
                           .requesterType(command.getRequesterType().name())
                           .requesterName(unwrap(command.getRequesterName()))
                           .requesterSurname(unwrap(command.getRequesterSurname()))
                           .build();
    }

    private AppendSheetCommand buildAppendSheetCommand(SheetRowData sheetRowData) {
        return AppendSheetCommand.builder()
                                 .spreadsheetId(spreadsheetId)
                                 .tableRange(tableRange)
                                 .rowData(sheetRowData)
                                 .build();
    }

}
