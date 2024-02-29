package com.straujupite.core.service;

import com.straujupite.common.dto.ActivityToDo;
import com.straujupite.out.adapter.AddTodoActivityAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AddTodoActivityService {

    private final AddTodoActivityAdapter addTodoActivityAdapter;

    public Mono<Void> addTodoActivity(ActivityToDo activityToDo) {
        return addTodoActivityAdapter.addTodoActivity(activityToDo.companyID(), activityToDo.deadline(), activityToDo.description());
    }
}
