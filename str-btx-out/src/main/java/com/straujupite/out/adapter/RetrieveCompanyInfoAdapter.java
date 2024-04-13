package com.straujupite.out.adapter;

import com.straujupite.common.dto.out.response.GetCompanyOutResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class RetrieveCompanyInfoAdapter {

  private static final String URI = "/crm.duplicate.findbycomm.json?entity_type=COMPANY&type=PHONE&values[]=";

  @Autowired
  private WebClient webClient;

  public Mono<GetCompanyOutResponse> retrieveCompanyIdByPhoneNumber(String phoneNumber) {
    return webClient.get()
                    .uri(URI + phoneNumber)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .onStatus(this::isClientOrServerError, error -> Mono.error(
                        new RuntimeException("Couldn't send request to Bitrix")))
                    .bodyToMono(GetCompanyOutResponse.class);
    }

  private boolean isClientOrServerError(HttpStatusCode statusCode) {
    return statusCode.is4xxClientError() || statusCode.is5xxServerError();
  }
}
