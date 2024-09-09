package com.straujupite.common.util.uriformatter;

import com.straujupite.common.config.BitrixEndpoints;
import com.straujupite.common.dto.out.request.AddActivityOutRequest;

class AddActivityToDealUriBuilder implements UriBuilder<AddActivityOutRequest> {

  @Override
  public String buildUri(AddActivityOutRequest request) {
    return String.format(
        BitrixEndpoints.ADD_ACTIVITY_TO_DEAL,
        request.getDealId(),
        request.getDeadline(),
        request.getDescription());
  }
}
