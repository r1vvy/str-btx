package com.straujupite.core.service.addcomment;

import com.straujupite.common.dto.common.bitrix.BtxComment;
import com.straujupite.common.dto.common.callInfo.RetrieveCallInfoEventType;
import com.straujupite.common.dto.context.RetrieveCallInfoContext;
import reactor.core.publisher.Mono;

public interface AddCommentEventTypeFlow {
  boolean isEventType(RetrieveCallInfoEventType eventType);

  Mono<BtxComment> createComment(RetrieveCallInfoContext context);

}
