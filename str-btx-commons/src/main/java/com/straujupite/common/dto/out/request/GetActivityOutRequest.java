package com.straujupite.common.dto.out.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetActivityOutRequest {

  private final String ownerTypeId;
  private final String ownerId;
  private final String authorId;
}
