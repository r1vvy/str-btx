package com.straujupite.out.converter;

import com.google.api.services.sheets.v4.model.ValueRange;
import com.straujupite.common.converter.ReactiveConverter;
import com.straujupite.common.dto.common.sheet.SheetRowData;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class SheetRowDataToValueRangeConverter implements ReactiveConverter<SheetRowData, ValueRange> {

    @Override
    public boolean canConvert(Class<?> sourceClass, Class<?> targetClass) {
        return SheetRowData.class.equals(sourceClass) && ValueRange.class.equals(targetClass);
    }

    @Override
    public Mono<ValueRange> convert(SheetRowData source, Class<ValueRange> targetClass) {
        return Mono.justOrEmpty(source)
                   .map(src -> {
                       var values = new ArrayList<>();
                       appendIfNotNull(values, source.getRequesterType());
                       appendIfNotNull(values, source.getRequesterName());
                       appendIfNotNull(values, source.getRequesterSurname());
                       appendIfNotNull(values, source.getOrgName());
                       appendIfNotNull(values, source.getOrgRegistrationNum());
                       appendIfNotNull(values, source.getRequesterPhone());
                       appendIfNotNull(values, source.getJurAddress());
                       appendIfNotNull(values, source.getDeliveryAddress());
                       appendIfNotNull(values, source.getPostalIndex());
                       appendIfNotNull(values, source.getCardCount());
                       appendIfNotNull(values, source.isSent());

                       return values;
                   })
                   .map(this::buildValueRange);
    }

    private void appendIfNotNull(List<Object> list, Object source) {
        if (source != null) {
            list.add(source);
        }
    }

    private ValueRange buildValueRange(List<Object> objectList) {
        return new ValueRange().setValues(List.of(objectList));
    }
}
