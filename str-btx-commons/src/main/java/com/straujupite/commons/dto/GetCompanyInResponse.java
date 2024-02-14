package com.straujupite.commons.dto;


import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.LinkedHashMap;

@Getter
@Setter
public class GetCompanyInResponse{

    private Object result;

    public String getCompanyID(){
        if (result instanceof ArrayList<?>) {
            return "0";
        }

        return ((LinkedHashMap<?, ?>) result).get("COMPANY").toString()
                                                            .replace("[", "")
                                                            .replace("]", "");

    }
}



