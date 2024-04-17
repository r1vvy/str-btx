package com.straujupite.core.service;

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
