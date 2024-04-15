package com.straujupite.common.util.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.straujupite.common.dto.out.response.GetActivityOutResponse;
import java.io.IOException;

public class GetActivityOutResponseDeserializer extends StdDeserializer<GetActivityOutResponse> {

  public GetActivityOutResponseDeserializer() {
    super(GetActivityOutResponse.class);
  }

  @Override
  public GetActivityOutResponse deserialize(JsonParser jp, DeserializationContext ctxt)
      throws IOException {
    JsonNode rootNode = jp.getCodec().readTree(jp);
    JsonNode resultNode = rootNode.path("result");

    if (resultNode.isArray() && !resultNode.isEmpty()) {
      JsonNode activityNode = resultNode.get(0);
      String id = activityNode.path("ID").asText();
      String deadline = activityNode.path("DEADLINE").asText();
      return new GetActivityOutResponse(id, deadline);
    }

    return null;
  }
}
