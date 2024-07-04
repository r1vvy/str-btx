package com.straujupite.itest.mock;

import org.springframework.stereotype.Component;

@Component
public class BitrixMock extends BaseMock {

  private static final String OUTGOING_JSON_PATH = "/json/out";

  public void stubGetCompanyIdByPhoneNumber(String requestFileName, String responseFileName) {
    stub(OUTGOING_JSON_PATH + "/getCompanyId/" + requestFileName,
        OUTGOING_JSON_PATH + "/getCompanyId/" + responseFileName);
  }

  public void stubAddComment(String requestFileName, String responseFileName) {
    stubMore(OUTGOING_JSON_PATH + "/addComment/" + requestFileName,
        OUTGOING_JSON_PATH + "/addComment/" + responseFileName);
  }

  public void stubGetLatestDealByCompanyId(String requestFileName, String responseFileName) {
    stubMore(OUTGOING_JSON_PATH + "/getLatestDeal/" + requestFileName,
        OUTGOING_JSON_PATH + "/getLatestDeal/" + responseFileName);
  }
}
