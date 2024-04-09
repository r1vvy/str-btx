package com.straujupite.out.adapter;

import com.straujupite.common.dto.out.request.UpdateActivityDeadlineOutRequest;
import com.straujupite.common.dto.out.response.UpdateActivityOutResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UpdateActivityAdapter {
  private static final String URI = "crm.activity.todo.updateDeadline?ownerTypeId=%s&ownerId=%s&id=%s&value=%s";

  @Autowired
  private WebClient webClient;

  public Mono<UpdateActivityOutResponse> updateActivityDeadline(
      UpdateActivityDeadlineOutRequest request) {
    return webClient.get()
                    .uri(formatURI(request))
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,
                        error -> Mono.error(
                            new RuntimeException("Could not establish connection with Bitrix")))
                    .bodyToMono(UpdateActivityOutResponse.class);
  }

  private String formatURI(UpdateActivityDeadlineOutRequest request) {
    return String.format(
        URI,
        request.getOwnerTypeId(),
        request.getOwnerId(),
        request.getId(),
        request.getUpdatedDeadline()
    );
  }
}
