package com.straujupite.out.adapter;

import com.straujupite.common.config.WebClientConfiguration;
import com.straujupite.common.dto.ActivityToDo;
import com.straujupite.common.dto.GetActivityIdInResponse;
import com.straujupite.common.error.BitrixError;
import lombok.RequiredArgsConstructor;
import org.springframework.core.codec.DecodingException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AddTodoActivityAdapter {

    private final WebClientConfiguration webClient;

    private static final String URI = "crm.activity.todo.add?ownerTypeId=4&ownerId=%d&deadline=%s&description=%s";

    public Mono<Void> addTodoActivity(ActivityToDo activityToDo) {

        //deadline format: "YYYY-MM-DDThh:mm:ss" ex: "2024-12-31T15:00:00"
        return webClient.webClient().get()
                .uri(String.format(URI, activityToDo.companyID(), activityToDo.deadline(), activityToDo.description()))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response.bodyToMono(com.straujupite.common.dto.BitrixError.class)
                                .flatMap(error -> Mono.error(new BitrixError(error.getErrorDescription()))))
                .bodyToMono(GetActivityIdInResponse.class)
                .onErrorMap(throwable -> {
                    if (throwable instanceof DecodingException) {
                        return new BitrixError("Failed to add activity");
                    }
                    return throwable;
                })
                .then();
    }

}
