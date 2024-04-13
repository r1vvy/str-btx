package com.straujupite.common.util.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.straujupite.common.dto.DealStage;
import java.io.IOException;

public class DealStageDeserializer extends StdDeserializer<DealStage> {

    public DealStageDeserializer() {
        super(DealStage.class);
    }

    @Override
    public DealStage deserialize(JsonParser jsonParser, DeserializationContext ctxt)
        throws IOException {
        String value = jsonParser.getValueAsString();
        return DealStage.getDealStage(value);
    }

}
