package com.straujupite.common.util.uriformatter;

import com.straujupite.common.dto.common.PhoneNumber;
import com.straujupite.common.dto.common.bitrix.BitrixEndpoints;

class GetCompanyInfoByPhoneNumberUriBuilder implements UriBuilder<PhoneNumber> {

  @Override
  public String buildUri(PhoneNumber phoneNumber) {
    return String.format(BitrixEndpoints.GET_COMPANY_INFO_BY_PHONE_NUMBER, phoneNumber.getValue());
  }
}
