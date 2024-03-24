package com.straujupite.common.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetCompanyOutResponse {

    @JsonProperty("COMPANY")
    private List<Integer> companies;
}






