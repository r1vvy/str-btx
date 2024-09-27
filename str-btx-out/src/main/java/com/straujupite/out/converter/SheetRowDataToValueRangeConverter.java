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
                       addValue(values, source.getRequesterType());
                       addValue(values, source.getRequesterName());
                       addValue(values, source.getRequesterSurname());
                       addValue(values, source.getOrgName());
                       addValue(values, source.getOrgRegistrationNum());
                       addValue(values, source.getRequesterEmail());
                       addValue(values, source.getRequesterPhone());
                       addValue(values, source.getJurAddress());
                       addValue(values, source.getDeliveryAddress());
                       addValue(values, source.getPostalIndex());
                       addValue(values, source.getCardCount());

                       return values;
                   })
                   .map(this::buildValueRange);
    }

    private boolean addValue(ArrayList<Object> values, Object source) {
        return values.add(source);
    }

    private ValueRange buildValueRange(List<Object> objectList) {
        return new ValueRange().setValues(List.of(objectList));
    }
}
