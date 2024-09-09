package com.straujupite.common.util.uriformatter;

import com.straujupite.common.config.BitrixEndpoints;
import com.straujupite.common.dto.common.PhoneNumber;

class GetCompanyInfoByPhoneNumberUriBuilder implements UriBuilder<PhoneNumber> {

  @Override
  public String buildUri(PhoneNumber phoneNumber) {
    return String.format(BitrixEndpoints.GET_COMPANY_INFO_BY_PHONE_NUMBER, phoneNumber.getValue());
  }
}
