package com.straujupite.core.service.changedealstage.flow;

import com.straujupite.common.dto.context.ChangeDealStageContext;
import reactor.core.publisher.Mono;

public interface ChangeDealStageFlow {

  boolean isSupported(ChangeDealStageContext context);

  Mono<Void> execute(ChangeDealStageContext context);
}
