package com.straujupite.common.dto.out.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.straujupite.common.util.deserializer.GetCompanyOutResponseDeserializer;
import com.straujupite.common.util.deserializer.UpdateActivityOutResponseDeserializer;
import lombok.Data;

@Data
@JsonDeserialize(using = UpdateActivityOutResponseDeserializer.class)
public class UpdateActivityOutResponse {

  private String resultId;
}
