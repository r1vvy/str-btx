package com.straujupite.common.webclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WebClientService {

  @Autowired
  private WebClient webClient;

  public <Response> Mono<Response> getAndReceiveMono(String uri, Class<Response> responseClass) {
    return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, this::handleError)
                    .bodyToMono(responseClass);
  }

  public <Response, Request> Mono<Response> postAndReceiveMono(String uri, Request request,
      Class<Response> responseClass) {
    return webClient.post()
                    .uri(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, this::handleError)
                    .bodyToMono(responseClass);

  }

  private Mono<Throwable> handleError(ClientResponse response) {
    return response.bodyToMono(String.class)
                   .flatMap(body -> Mono.error(
                       new RuntimeException("Failed outgoing request: " + body)));
  }
}
