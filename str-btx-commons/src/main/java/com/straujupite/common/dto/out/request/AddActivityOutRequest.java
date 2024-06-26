package com.straujupite.common.dto.out.request;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class AddActivityOutRequest {

  private final String dealId;
  private final String deadline;
  private final String description;
}