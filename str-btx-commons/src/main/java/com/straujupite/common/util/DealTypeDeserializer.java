package com.straujupite.common.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.straujupite.common.dto.DealType;

public class DealTypeDeserializer extends StdDeserializer<DealType> {

    public DealTypeDeserializer() {
        super(DealType.class);
    }

    @Override
    public DealType deserialize(JsonParser jsonParser, DeserializationContext ctxt)
            throws IOException, JacksonException {
        String value = jsonParser.getValueAsString();
        return DealType.getDealType(value);
    }

}
