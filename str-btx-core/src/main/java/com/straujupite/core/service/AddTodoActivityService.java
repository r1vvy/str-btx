package com.straujupite.core.service;

import com.straujupite.common.dto.GetActivityIdInResponse;
import com.straujupite.out.adapter.AddTodoActivityAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AddTodoActivityService {

    private final AddTodoActivityAdapter addTodoActivityAdapter;

    public Mono<GetActivityIdInResponse> addTodoActivity(Integer companyID, String deadline, String description) {
        return addTodoActivityAdapter.addTodoActivity(companyID, deadline, description);
    }
}
