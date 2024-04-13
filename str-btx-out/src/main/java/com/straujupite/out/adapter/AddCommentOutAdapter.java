package com.straujupite.out.adapter;

import com.straujupite.common.dto.out.command.AddCommentOutCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AddCommentOutAdapter {

  private static final String URI = "/crm.timeline.comment.add";

  @Autowired
  private WebClient webClient;

  public Mono<Void> addComment(AddCommentOutCommand command) {
    return webClient.post()
                    .uri(URI)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(command))
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, this::handleError)
                    .toBodilessEntity()
                    .then();
  }

  private Mono<Throwable> handleError(ClientResponse response) {
    return response.bodyToMono(String.class)
                   .flatMap(
                       body -> Mono.error(new RuntimeException("Failed to add comment: " + body)));
  }
}
