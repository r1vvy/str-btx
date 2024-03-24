package com.straujupite.core.service;

import com.straujupite.common.dto.GetStageID;
import com.straujupite.common.dto.context.RetrieveCallInfoContext;
import com.straujupite.out.adapter.RetrieveLatestDealAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RetrieveLatestCompanyDealService {

  private final RetrieveLatestDealAdapter retrieveLatestDealAdapter;

  public Mono<GetStageID> retrieveLatestDealStage(
    RetrieveCallInfoContext context
  ) {
    return retrieveLatestDealAdapter.retrieveLatestDeal(context.getCompanyId());
  }
}
