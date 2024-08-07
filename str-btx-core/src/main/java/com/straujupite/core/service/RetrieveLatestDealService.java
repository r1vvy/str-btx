package com.straujupite.core.service;

import static com.straujupite.common.util.ReactorMdcUtil.logOnError;
import static com.straujupite.common.util.ReactorMdcUtil.logOnNext;

import com.straujupite.common.dto.DealInfo;
import com.straujupite.common.dto.common.bitrix.CompanyId;
import com.straujupite.common.dto.context.RetrieveCallInfoContext;
import com.straujupite.common.dto.out.response.GetCompanyDealsOutResponse;
import com.straujupite.out.adapter.BitrixAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class RetrieveLatestDealService {

	private final BitrixAdapter bitrixAdapter;

	public Mono<RetrieveCallInfoContext> retrieveLatestDealInfo(RetrieveCallInfoContext context,
			String companyId) {
		return Mono.justOrEmpty(companyId)
							 .map(CompanyId::new)
							 .flatMap(bitrixAdapter::retrieveDealsByCompanyId)
							 .map(this::getLatestDeal)
							 .doOnEach(logOnNext(deal -> log.info("Retrieved latest deal: {}", deal)))
							 .map(context::withDealInfo)
							 .doOnEach(logOnError(err -> log.error("Error while retrieving latest deal info: {}",
									 err.getMessage())))
							 .onErrorResume(err -> Mono.empty())
							 .defaultIfEmpty(context);
	}

	private DealInfo getLatestDeal(GetCompanyDealsOutResponse response) {
		return response.getDeals()
									 .stream()
									 .findFirst()
									 .orElseThrow(() -> new RuntimeException("No deals found"));
	}
}
