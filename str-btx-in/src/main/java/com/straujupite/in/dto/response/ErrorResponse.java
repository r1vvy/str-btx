package com.straujupite.in.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ErrorResponse {
    private String traceId;
    private String message;
    private List<String> details;
}
