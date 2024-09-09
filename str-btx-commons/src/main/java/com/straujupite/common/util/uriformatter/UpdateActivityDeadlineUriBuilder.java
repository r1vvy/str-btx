package com.straujupite.common.util.uriformatter;

import com.straujupite.common.config.BitrixEndpoints;
import com.straujupite.common.dto.out.request.UpdateActivityDeadlineOutRequest;

class UpdateActivityDeadlineUriBuilder implements UriBuilder<UpdateActivityDeadlineOutRequest> {

  @Override
  public String buildUri(UpdateActivityDeadlineOutRequest request) {
    return String.format(
        BitrixEndpoints.UPDATE_ACTIVITY_DEADLINE,
        request.getOwnerTypeId(),
        request.getOwnerId(),
        request.getId(),
        request.getUpdatedDeadline()
    );
  }

}
