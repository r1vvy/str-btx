package com.straujupite.core.service;

import static com.straujupite.common.util.ReactorMdcUtil.logOnError;
import static com.straujupite.common.util.ReactorMdcUtil.logOnNext;

import com.straujupite.common.dto.DealInfo;
import com.straujupite.common.dto.common.bitrix.CompanyId;
import com.straujupite.common.dto.context.RetrieveCallInfoContext;
import com.straujupite.common.dto.out.response.GetCompanyDealsOutResponse;
import com.straujupite.out.adapter.BitrixAdapter;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class RetrieveDealService {

  private final BitrixAdapter bitrixAdapter;

  public Mono<RetrieveCallInfoContext> retrieveLatestDealInfo(RetrieveCallInfoContext context,
                                                              String companyId) {
    return getDealsByCompanyId(companyId).map(this::getLatestDeal)
                                         .doOnEach(logOnNext(
                                             deal -> log.info("Retrieved latest deal: {}", deal)))
                                         .map(Collections::singletonList)
                                         .flatMap(
                                             deals -> addDealsToContextIfPresent(context, deals))
                                         .doOnEach(logOnError(this::logError))
                                         .onErrorResume(err -> Mono.empty())
                                         .defaultIfEmpty(context);
  }

  public Mono<RetrieveCallInfoContext> retrieveAllDealsByCompanyId(RetrieveCallInfoContext context,
                                                                   String companyId) {
    return getDealsByCompanyId(companyId).doOnEach(
                                             logOnNext(deals -> log.info("Retrieved deals: {}", deals)))
                                         .flatMap(
                                             deals -> addDealsToContextIfPresent(context, deals))
                                         .doOnEach(logOnError(this::logError))
                                         .onErrorResume(err -> Mono.empty())
                                         .defaultIfEmpty(context);
  }

  private Mono<List<DealInfo>> getDealsByCompanyId(String companyId) {
    return Mono.justOrEmpty(companyId)
               .map(CompanyId::new)
               .flatMap(bitrixAdapter::retrieveDealsByCompanyId)
               .map(GetCompanyDealsOutResponse::getDeals);
  }

  private DealInfo getLatestDeal(List<DealInfo> deals) {
    return deals.stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No deals found"));
  }

  private Mono<RetrieveCallInfoContext> addDealsToContextIfPresent(RetrieveCallInfoContext context,
                                                                   List<DealInfo> deals) {
    return Mono.justOrEmpty(deals)
               .map(context::withDeals);
  }

  private void logError(Throwable err) {
    log.error("Error while retrieving latest deal info: {}",
              err.getMessage());
  }

}
