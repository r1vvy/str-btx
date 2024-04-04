package com.straujupite.out.adapter;

import com.straujupite.common.dto.BitrixError;
import com.straujupite.common.dto.out.request.AddActivityOutRequest;
import com.straujupite.common.dto.out.response.AddActivityOutResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.codec.DecodingException;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AddActivityAdapter {

    private final WebClient webClient;

    private static final String URI = "crm.activity.todo.add?ownerTypeId=4&ownerId=%d&deadline=%s&description=%s";

  public Mono<Void> addActivity(AddActivityOutRequest addActivityOutRequest) {

        return webClient.get()
                        .uri(String.format(URI, addActivityOutRequest.getCompanyID(),
                            addActivityOutRequest.getDeadline(),
                            addActivityOutRequest.getDescription()))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                    response -> response.bodyToMono(BitrixError.class)
                                        .flatMap(error -> Mono.error(
                                            new RuntimeException(error.getErrorDescription()))))
                        .bodyToMono(AddActivityOutResponse.class)
                .onErrorMap(throwable -> {
                    if (throwable instanceof DecodingException) {
                      return new RuntimeException("Failed to add activity");
                    }
                    return throwable;
                })
                .then();
    }

}
