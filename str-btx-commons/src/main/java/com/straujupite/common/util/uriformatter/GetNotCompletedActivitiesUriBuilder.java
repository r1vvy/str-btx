package com.straujupite.common.util.uriformatter;

import com.straujupite.common.dto.common.bitrix.BitrixEndpoints;
import com.straujupite.common.dto.out.request.GetActivityOutRequest;

class GetNotCompletedActivitiesUriBuilder implements UriBuilder<GetActivityOutRequest> {

  @Override
  public String buildUri(GetActivityOutRequest request) {
    return String.format(
        BitrixEndpoints.GET_NOT_COMPLETED_ACTIVITIES,
        request.getOwnerTypeId(),
        request.getOwnerId(),
        request.getAuthorId()
    );
  }
}
