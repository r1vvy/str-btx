package com.straujupite.common.config;

import static com.straujupite.common.util.FormatterUtil.formatJson;
import static com.straujupite.common.util.ReactorMdcUtil.logOnNext;

import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;

@Slf4j
public class WebClientFilter {

  public ExchangeFilterFunction logResponse() {
    return ExchangeFilterFunction.ofResponseProcessor(response ->
        response.bodyToMono(String.class)
                .flatMap(body -> mutateAndLogResponse(response, body)));
  }

  private Mono<ClientResponse> mutateAndLogResponse(ClientResponse response, String body) {
    return Mono.just(mutateResponse(response, body))
               .doOnEach(logResponse(response, formatJson(body)));
  }

  private ClientResponse mutateResponse(ClientResponse response, String body) {
    return response.mutate()
                   .body(body)
                   .build();
  }

  private Consumer<Signal<ClientResponse>> logResponse(ClientResponse response,
      String formattedBody) {
    return logOnNext(res -> log.debug(buildResponseLog(formattedBody, response)));
  }

  private String buildResponseLog(String body, ClientResponse response) {
    return String.format(
        """
                    
            ----------------OUTGOING RESPONSE----------------
            STATUS: %s
            BODY:
            %s
            """,
        response.statusCode().value(), body
    );
  }
}
