package com.straujupite.common.util.uriformatter;

import com.straujupite.common.config.BitrixEndpoints;
import com.straujupite.common.dto.common.bitrix.CompanyId;

class GetNotCompletedDealsByCompanyIdUriBuilder implements UriBuilder<CompanyId> {

  @Override
  public String buildUri(CompanyId companyId) {
    return String.format(BitrixEndpoints.GET_NOT_COMPLETED_DEALS_BY_COMPANY_ID,
        companyId.getValue());
  }
}
