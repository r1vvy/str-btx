
package com.straujupite.out.adapter;

import com.straujupite.common.dto.BitrixError;
import com.straujupite.common.dto.out.request.GetActivityOutRequest;
import com.straujupite.common.dto.out.response.GetActivityOutResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.codec.DecodingException;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class GetActivityAdapter {

  private static final String URI = "crm.activity.list?filter[OWNER_TYPE_ID]=%s&filter[OWNER_ID]=%s";

  private final WebClient webClient;


  public Mono<GetActivityOutResponse> getActivity(GetActivityOutRequest request) {
    return webClient.get()
                    .uri(String.format(URI, request.getOwnerTypeId(), request.getOwnerId()))
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,
                        response -> response.bodyToMono(BitrixError.class)
                                            .flatMap(error -> Mono.error(
                                                new RuntimeException(error.getErrorDescription()))))
                    .bodyToMono(GetActivityOutResponse.class)
                    .onErrorMap(throwable -> {
                      if (throwable instanceof DecodingException) {
                        return new RuntimeException("Failed to get activity");
                      }
                      return throwable;
                    });
  }

}
