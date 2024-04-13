package com.straujupite.core.service.addcomment.lostcalleradded;

import com.straujupite.common.dto.common.bitrix.BtxComment;
import com.straujupite.common.dto.common.callInfo.RetrieveCallInfoEventType;
import com.straujupite.common.dto.context.RetrieveCallInfoContext;
import com.straujupite.common.util.addcomment.CommentBuilder;
import com.straujupite.core.service.addcomment.AddCommentEventTypeFlow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class LostCallerAddedFlowService implements AddCommentEventTypeFlow {

  private static final String FLOW_TEMPLATE = "Klientam neizdevās sazvanīt tālr. nr. %s (%s). %s plkst. %s";

  private final CommentBuilder commentBuilder;

  @Override
  public boolean isEventType(RetrieveCallInfoEventType eventType) {
    return RetrieveCallInfoEventType.LOST_CALLER_ADDED.equals(eventType);
  }

  @Override
  public Mono<BtxComment> createComment(RetrieveCallInfoContext context) {
    return Mono.justOrEmpty(buildCommentByTemplate(context));
  }

  private BtxComment buildCommentByTemplate(RetrieveCallInfoContext context) {
    return commentBuilder.buildCommentByTemplate(
        LostCallerAddedFlowService.FLOW_TEMPLATE,
        context.getRetrieveCallInfoCommand().getCallInfo().getLastContactDateTime(),
        context.getStrNumber(),
        context.getRetrieveCallInfoCommand().getCallInfo().getContactName()
    );
  }
}
