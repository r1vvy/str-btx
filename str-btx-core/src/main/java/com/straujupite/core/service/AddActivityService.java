package com.straujupite.core.service;

import com.straujupite.common.dto.out.request.AddActivityOutRequest;
import com.straujupite.out.adapter.AddActivityAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AddActivityService {

  private final AddActivityAdapter addActivityAdapter;

  public Mono<Void> addActivity(AddActivityOutRequest addActivityOutRequest) {
    return addActivityAdapter.addActivity(addActivityOutRequest);
  }
}
