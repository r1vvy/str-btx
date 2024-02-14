package com.straujupite.in.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
public class CallInfoResponse {

  private final String responseId;
}
