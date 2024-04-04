package com.straujupite.core.service;

import com.straujupite.common.dto.DealInfo;
import com.straujupite.common.dto.context.RetrieveCallInfoContext;
import com.straujupite.common.dto.out.response.GetCompanyDealsOutResponse;
import com.straujupite.out.adapter.RetrieveDealsAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RetrieveLatestDealService {

	private final RetrieveDealsAdapter retrieveDealsAdapter;

	public Mono<RetrieveCallInfoContext> retrieveLatestDealInfo(RetrieveCallInfoContext context,
			String companyID) {
		return retrieveDealsAdapter.retrieveDealsByCompanyId(companyID)
															 .map(this::getLatestDeal)
															 .map(context::withDealInfo);
	}

	private DealInfo getLatestDeal(GetCompanyDealsOutResponse response) {
		return response.getDeals()
									 .stream()
									 .findFirst()
									 .orElseThrow(() -> new RuntimeException("No deals found"));
	}
}
