package com.straujupite.common.util.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.straujupite.common.dto.DealStage;
import com.straujupite.common.dto.out.response.GetCompanyOutResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetCompanyOutResponseDeserializer extends StdDeserializer<GetCompanyOutResponse> {

  public GetCompanyOutResponseDeserializer() {
    super(DealStage.class);
  }

  @Override
  public GetCompanyOutResponse deserialize(JsonParser jp, DeserializationContext ctxt)
      throws IOException {
    JsonNode node = jp.getCodec().readTree(jp);
    List<Integer> companies = new ArrayList<>();

    JsonNode companiesNode = node.path("result").path("COMPANY");
    if (!companiesNode.isMissingNode() && companiesNode.isArray()) {
      for (JsonNode companyNode : companiesNode) {
        companies.add(companyNode.asInt());
      }
    }
    return new GetCompanyOutResponse(companies);
  }
}