package com.straujupite.core.service;

import com.straujupite.common.dto.DealInfo;
import com.straujupite.common.dto.context.RetrieveCallInfoContext;
import com.straujupite.common.dto.out.response.GetDealInfoOutResponse;
import com.straujupite.out.adapter.RetrieveLatestDealAdapter;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RetrieveLatestDealService {

	private final RetrieveLatestDealAdapter retrieveLatestDealAdapter;

	public Mono<RetrieveCallInfoContext> retrieveLatestDealStage(RetrieveCallInfoContext context, String companyID) {
		return retrieveLatestDealAdapter.retrieveLatestDeal(companyID)
				.map(this::getLatestDeal)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.map(context::withDealInfo)
				.switchIfEmpty(Mono.error(new RuntimeException("No deals found")));
	}

	private Optional<DealInfo> getLatestDeal(GetDealInfoOutResponse response) {
		return response.getDeals()
				.stream()
				.findFirst();
	}
}
