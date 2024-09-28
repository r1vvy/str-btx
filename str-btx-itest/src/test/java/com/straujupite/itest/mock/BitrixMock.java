package com.straujupite.itest.mock;

import org.springframework.stereotype.Component;

@Component
public class BitrixMock extends BaseMock {

  public void stubGetCompanyIdByPhoneNumber(String responseFileName) {
    stubGet("/crm.duplicate.findbycomm.json",
            200,
        OUTGOING_JSON_PATH + "/getCompanyId/" + responseFileName);
  }

  public void stubAddComment(String requestFileName, String responseFileName) {
    stubPost("/crm.timeline.comment.add",
            200,
            OUTGOING_JSON_PATH + "/addComment/" + requestFileName,
            OUTGOING_JSON_PATH + "/addComment/" + responseFileName
    );
  }

  public void stubGetLatestDealByCompanyId(String responseFileName) {
    stubGet("/crm.deal.list.json",
            200,
            OUTGOING_JSON_PATH + "/getLatestDeal/" + responseFileName);
  }
}
