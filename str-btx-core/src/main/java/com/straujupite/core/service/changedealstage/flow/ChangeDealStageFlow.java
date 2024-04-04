package com.straujupite.core.service.changedealstage.flow;

import com.straujupite.common.dto.context.RetrieveCallInfoContext;
import reactor.core.publisher.Mono;

public interface ChangeDealStageFlow {

  boolean isSupported(RetrieveCallInfoContext context);

  Mono<Void> execute(RetrieveCallInfoContext context);
}
