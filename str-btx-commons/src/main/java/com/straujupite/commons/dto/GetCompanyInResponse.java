package com.straujupite.commons.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetCompanyInResponse{

    //Don't know why this needs to be here but without this it doesn't work
    @JsonProperty("result")
    private GetCompanyInResponse result;

    @JsonProperty("COMPANY")
    private Integer[] COMPANY;

    private Integer companyID;

    public Integer getCompanyID(){
        return result.COMPANY[0];
    }

}



