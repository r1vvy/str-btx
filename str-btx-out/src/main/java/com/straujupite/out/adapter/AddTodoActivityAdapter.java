package com.straujupite.out.adapter;

import com.straujupite.common.config.WebClientConfiguration;
import com.straujupite.common.dto.GetActivityIdInResponse;
import com.straujupite.common.error.BitrixError;
import lombok.RequiredArgsConstructor;
import org.springframework.core.codec.DecodingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AddTodoActivityAdapter {

    private final WebClientConfiguration webClient;

    private static final String URI = "crm.activity.todo.add?ownerTypeId=4&ownerId=%d&deadline=%s&description=%s";

    public Mono<GetActivityIdInResponse> addTodoActivity(Integer companyID, String deadline, String description) {
        //deadline format: "YYYY-MM-DDThh:mm:ss" ex: "2024-12-31T15:00:00"
        return webClient.webClient().get()
                .uri(String.format(URI, companyID, deadline, description))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->{
                    if (response.statusCode().equals(HttpStatus.BAD_REQUEST)){
                        return Mono.error(new BitrixError("Wrong parameters"));
                    } else{
                        return Mono.error(new BitrixError("Could not establish connection with Bitrix"));
                    }
                })
                .bodyToMono(GetActivityIdInResponse.class)
                .onErrorMap(throwable -> {
                    if (throwable instanceof DecodingException) {
                        return new BitrixError("Failed to add activity");
                    }
                    return throwable;
                });
    }
}