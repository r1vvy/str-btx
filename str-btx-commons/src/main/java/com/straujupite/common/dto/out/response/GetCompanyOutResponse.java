package com.straujupite.common.dto.out.response;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.straujupite.common.util.deserializer.GetCompanyOutResponseDeserializer;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(using = GetCompanyOutResponseDeserializer.class)
public class GetCompanyOutResponse {
  private List<Integer> companies;
}






