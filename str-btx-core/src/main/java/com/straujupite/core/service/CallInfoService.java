package com.straujupite.core.service;

import com.straujupite.common.dto.context.RetrieveCallInfoContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CallInfoService {

  public Mono<Void> retrieveCallInfo(RetrieveCallInfoContext context) {
    return Mono.justOrEmpty(context)
               .then();
  }
}
