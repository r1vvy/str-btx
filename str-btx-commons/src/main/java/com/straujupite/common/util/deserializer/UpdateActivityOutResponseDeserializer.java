package com.straujupite.common.util.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.straujupite.common.dto.out.response.UpdateActivityOutResponse;

import java.io.IOException;

public class UpdateActivityOutResponseDeserializer extends StdDeserializer<UpdateActivityOutResponse> {

    public UpdateActivityOutResponseDeserializer() {
        super(UpdateActivityOutResponse.class);
    }

    @Override
    public UpdateActivityOutResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec()
                .readTree(jsonParser);

        JsonNode resultNode = node.path("result");
        String resultId = resultNode.path("id").asText();

        UpdateActivityOutResponse response = new UpdateActivityOutResponse();
        response.setResultId(resultId);

        return response;
    }
}
