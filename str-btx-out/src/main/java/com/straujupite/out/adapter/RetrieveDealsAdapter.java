package com.straujupite.out.adapter;

import com.straujupite.common.dto.out.response.GetCompanyDealsOutResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RetrieveDealsAdapter {
  private static final String URI = "crm.deal.list.json?FILTER[COMPANY_ID]=%s&ORDER[DATE_CREATE]=DESC&FILTER[STAGE_SEMANTIC_ID]=P";

  @Autowired
  private WebClient webClient;

  public Mono<GetCompanyDealsOutResponse> retrieveDealsByCompanyId(String companyID) {
    return webClient.get()
                    .uri(String.format(URI, companyID))
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,
                        error -> Mono.error(
                            new RuntimeException("Could not establish connection with Bitrix")))
                    .bodyToMono(GetCompanyDealsOutResponse.class);

  }
}
