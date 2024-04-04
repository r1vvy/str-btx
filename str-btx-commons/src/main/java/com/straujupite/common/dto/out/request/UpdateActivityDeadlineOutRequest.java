package com.straujupite.common.dto.out.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateActivityDeadlineOutRequest {

  @NotNull
  private String ownerTypeId;
  @NotNull
  private String ownerId;
  @NotNull
  private String id;
  @NotNull
  private String updatedDeadline;
}
