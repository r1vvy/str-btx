package com.straujupite.out.adapter;

import com.straujupite.common.dto.ActivityToDo;
import com.straujupite.common.dto.out.response.GetActivityIdOutResponse;
import com.straujupite.common.error.BitrixError;
import lombok.RequiredArgsConstructor;
import org.springframework.core.codec.DecodingException;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AddTodoActivityAdapter {

    private final WebClient webClient;

    private static final String URI = "crm.activity.todo.add?ownerTypeId=4&ownerId=%d&deadline=%s&description=%s";

    public Mono<Void> addTodoActivity(ActivityToDo activityToDo) {

        // deadline format: "YYYY-MM-DDThh:mm:ss" ex: "2024-12-31T15:00:00"
        return webClient.get()
                .uri(String.format(URI, activityToDo.companyID(), activityToDo.deadline(), activityToDo.description()))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> response.bodyToMono(com.straujupite.common.dto.BitrixError.class)
                                .flatMap(error -> Mono.error(new BitrixError(error.getErrorDescription()))))
                .bodyToMono(GetActivityIdOutResponse.class)
                .onErrorMap(throwable -> {
                    if (throwable instanceof DecodingException) {
                        return new BitrixError("Failed to add activity");
                    }
                    return throwable;
                })
                .then();
    }

}
