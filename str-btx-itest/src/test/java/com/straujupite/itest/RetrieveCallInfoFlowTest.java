package com.straujupite.itest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.straujupite.common.dto.common.PhoneNumber;
import com.straujupite.common.dto.common.bitrix.CompanyId;
import com.straujupite.common.dto.out.command.AddCommentOutCommand;
import com.straujupite.itest.mock.BitrixMock;
import com.straujupite.out.adapter.BitrixAdapter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.test.StepVerifier;

public class RetrieveCallInfoFlowTest extends BaseIntegrationTest {

  @SpyBean
  BitrixAdapter bitrixAdapter;
  @Autowired
  private BitrixMock bitrixMock;

  @AfterEach
  void stop() {
    bitrixMock.reset();
  }

  @Test
  public void shouldAddCommentToCompany_WithoutLatestDeal_Flow() {
    bitrixMock.stubGetCompanyIdByPhoneNumber("/retrieveCompanyIdByPhoneNumberSuccessRequest.json",
        "/retrieveCompanyIdByPhoneNumberSuccessResponse.json");
    bitrixMock.stubAddComment("/addCommentSuccessRequest.json", "/addCommentSuccessResponse.json");
    bitrixMock.stubGetLatestDealByCompanyId("/getLatestDealByCompanyIdSuccessRequest.json",
        "/getLatestDealByCompanyIdSuccessNoDealResponse.json");
    var request = getJson("/in/retrieveCallInfoNoDealCompanySuccessRequest.json");

    retrieveCallInfo(request);

    verifyGetCompanyIdCalled();
    verifyAddCommentCalled();
    verifyGetLatestDealByCompanyIdCalled_NoDealsReturned();
    verify(bitrixAdapter, never()).changeStage(any());
    verify(bitrixAdapter, never()).getActivity(any());
    verify(bitrixAdapter, never()).updateActivityDeadline(any());
    verify(bitrixAdapter, never()).addActivity(any());
  }

  void verifyGetCompanyIdCalled() {
    var captor = ArgumentCaptor.forClass(PhoneNumber.class);
    verify(bitrixAdapter).retrieveCompanyIdByPhoneNumber(captor.capture());
    var command = captor.getValue();

    StepVerifier.create(bitrixAdapter.retrieveCompanyIdByPhoneNumber(command))
                .assertNext(response -> {
                  assertNotNull(response.getCompanies().get(0));
                  assertEquals(1, response.getCompanies().size());
                  assertEquals(1234, response.getCompanies().get(0));
                })
                .verifyComplete();
  }

  void verifyAddCommentCalled() {
    var captor = ArgumentCaptor.forClass(AddCommentOutCommand.class);
    verify(bitrixAdapter).addComment(captor.capture());
    var command = captor.getValue();

    StepVerifier.create(bitrixAdapter.addComment(command))
                .verifyComplete();
  }

  void verifyGetLatestDealByCompanyIdCalled_NoDealsReturned() {
    var captor = ArgumentCaptor.forClass(CompanyId.class);
    verify(bitrixAdapter).retrieveDealsByCompanyId(captor.capture());
    var command = captor.getValue();

    StepVerifier.create(bitrixAdapter.retrieveDealsByCompanyId(command))
                .assertNext(response -> {
                  assertNotNull(response);
                  assertEquals(0, response.getDeals().size());
                })
                .verifyComplete();
  }
}