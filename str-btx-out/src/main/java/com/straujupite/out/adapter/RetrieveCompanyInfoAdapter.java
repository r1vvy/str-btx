package com.straujupite.out.adapter;

import com.straujupite.common.dto.out.response.GetCompanyOutResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RetrieveCompanyInfoAdapter {
  private static final String URI = "crm.duplicate.findbycomm.json?entity_type=COMPANY&type=PHONE&values[]=";

  private final WebClient webClient;

  public Mono<GetCompanyOutResponse> retrieveCompanyByPhoneNumber(String phoneNumber) {
    return webClient.get()
                    .uri(URI + phoneNumber)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, error -> Mono.error(new RuntimeException("Could not establish connection with Bitrix")))
                    .bodyToMono(GetCompanyOutResponse.class);
    }
}
