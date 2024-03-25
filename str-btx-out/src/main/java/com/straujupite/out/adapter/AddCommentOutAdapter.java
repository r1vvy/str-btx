package com.straujupite.out.adapter;

import com.straujupite.common.dto.out.command.AddCommentOutCommand;
import com.straujupite.common.error.BitrixError;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AddCommentOutAdapter {

  private static final String URI = "crm.timeline.comment.add";

  @Autowired
  private WebClient webClient;

  public Mono<Void> addComment(AddCommentOutCommand command) {
    return webClient.post()
                    .uri(URI)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(command))
                    .retrieve()
                    .onStatus(this::isClientOrServerError, error -> createBitrixError("Could not add comment"))
                    .toBodilessEntity()
                    .then();
  }

  private boolean isClientOrServerError(HttpStatusCode statusCode) {
    return statusCode.is4xxClientError() || statusCode.is5xxServerError();
  }

  private Mono<Error> createBitrixError(String message) {
    return Mono.error(new BitrixError(message));
  }
}
