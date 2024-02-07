package com.straujupite.core.service;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CallInfoService {

  public Mono<Object> retrieveCallInfo(Object incomingRequest) {
    return Mono.justOrEmpty(incomingRequest);
  }

}
