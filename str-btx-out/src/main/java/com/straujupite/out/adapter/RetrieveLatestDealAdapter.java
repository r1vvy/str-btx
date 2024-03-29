package com.straujupite.out.adapter;

import com.straujupite.common.dto.out.response.GetDealInfoOutResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RetrieveLatestDealAdapter {
	private static final String URI = "crm.deal.list.json?FILTER[COMPANY_ID]=%s&ORDER[DATE_CREATE]=DESC&FILTER[STAGE_SEMANTIC_ID]=P";

	private final WebClient webClient;

	public Mono<GetDealInfoOutResponse> retrieveLatestDeal(String companyID) {
		return webClient.get()
										.uri(String.format(URI, companyID))
										.retrieve()
										.onStatus(HttpStatusCode::is4xxClientError,
												error -> Mono.error(new RuntimeException("Could not establish connection with Bitrix")))
										.bodyToMono(GetDealInfoOutResponse.class);

	}
}
