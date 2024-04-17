package com.straujupite.out.adapter;

import com.straujupite.common.dto.out.request.ChangeDealStageOutRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ChangeDealStageAdapter {

  private static final String URI = "/crm.deal.update.json";
  @Autowired
  private WebClient webClient;

  public Mono<Void> changeStage(ChangeDealStageOutRequest outRequest) {

    return webClient.post()
                    .uri(String.format(URI))
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(outRequest)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,
                        response -> response.bodyToMono(String.class)
                                            .doOnNext(
                                                System.out::println)  // Print API error response
                                            .flatMap(error -> Mono.error(
                                                new RuntimeException("API error: " + error))))
                    .toBodilessEntity()
                    .then();
  }
}
