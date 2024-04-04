package com.straujupite.out.adapter;

import com.straujupite.common.dto.BitrixError;
import com.straujupite.common.dto.out.request.ChangeDealStageOutRequest;
import com.straujupite.common.dto.out.response.ChangeDealStageOutResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.codec.DecodingException;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ChangeDealStageAdapter {

  private static final String URI = "crm.deal.update";
  @Autowired
  private WebClient webClient;

  public Mono<ChangeDealStageOutResponse> changeStage(ChangeDealStageOutRequest outRequest) {
    return webClient.post()
                    .uri(String.format(URI))
                    .bodyValue(outRequest)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,
                        response -> response.bodyToMono(BitrixError.class)
                                            .flatMap(error -> Mono.error(
                                                new RuntimeException(error.getErrorDescription()))))
                    .bodyToMono(ChangeDealStageOutResponse.class)
                    .onErrorMap(throwable -> {
                      if (throwable instanceof DecodingException) {
                        return new RuntimeException("Failed change deal stage");
                      }
                      return throwable;
                    });
  }
}
