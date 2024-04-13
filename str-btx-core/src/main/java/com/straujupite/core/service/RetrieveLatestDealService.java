package com.straujupite.core.service;

import com.straujupite.common.dto.DealInfo;
import com.straujupite.common.dto.context.RetrieveCallInfoContext;
import com.straujupite.common.dto.out.response.GetCompanyDealsOutResponse;
import com.straujupite.out.adapter.RetrieveDealsAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class RetrieveLatestDealService {

	private final RetrieveDealsAdapter retrieveDealsAdapter;

	public Mono<RetrieveCallInfoContext> retrieveLatestDealInfo(RetrieveCallInfoContext context,
			String companyID) {
		return retrieveDealsAdapter.retrieveDealsByCompanyId(companyID)
															 .map(this::getLatestDeal)
															 .doOnNext(deal -> log.debug("Retrieved latest deal: {}", deal))
															 .map(context::withDealInfo)
															 .onErrorResume(err -> {
																 log.debug("Error while retrieving latest deal info: {}",
																		 err.getMessage());
																 return Mono.empty();
															 }).defaultIfEmpty(context);
	}

	private DealInfo getLatestDeal(GetCompanyDealsOutResponse response) {
		return response.getDeals()
									 .stream()
									 .findFirst()
									 .orElseThrow(() -> new RuntimeException("No deals found"));
	}
}
