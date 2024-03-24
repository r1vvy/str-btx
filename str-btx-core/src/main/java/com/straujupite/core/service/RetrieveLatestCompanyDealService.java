package com.straujupite.core.service;

import com.straujupite.common.dto.GetStageID;
import com.straujupite.common.dto.GetStageIDInResponse;
import com.straujupite.common.dto.context.RetrieveCallInfoContext;
import com.straujupite.out.adapter.RetrieveLatestDealAdapter;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RetrieveLatestCompanyDealService {

	private final RetrieveLatestDealAdapter retrieveLatestDealAdapter;

	public Mono<RetrieveCallInfoContext> retrieveLatestDealStage(RetrieveCallInfoContext context, String companyID) {
		return retrieveLatestDealAdapter.retrieveLatestDeal(companyID)
				.map(this::getStageID)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.map(context::withDealStage)
				.switchIfEmpty(Mono.error(new RuntimeException("No deals found")));
	}

	private Optional<GetStageID> getStageID(GetStageIDInResponse response) {
		return response.getStages()
				.stream()
				.findFirst();
	}
}
