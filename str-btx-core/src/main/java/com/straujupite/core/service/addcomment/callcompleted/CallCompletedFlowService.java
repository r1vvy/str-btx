package com.straujupite.core.service.addcomment.callcompleted;

import com.straujupite.common.dto.common.bitrix.BtxComment;
import com.straujupite.common.dto.common.callInfo.CallDirection;
import com.straujupite.common.dto.common.callInfo.RetrieveCallInfoEventType;
import com.straujupite.common.dto.context.RetrieveCallInfoContext;
import com.straujupite.common.dto.in.command.RetrieveCallInfoCommand;
import com.straujupite.common.util.addcomment.CommentBuilder;
import com.straujupite.core.service.addcomment.AddCommentEventTypeFlow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CallCompletedFlowService implements AddCommentEventTypeFlow {
  private static final String CALL_SUCCESSFUL_TEMPLATE = "Zvans ar klientu (tālr. nr. %s) no tālr. nr. %s. pabeigts. %s plkst. %s";
  private static final String CALL_UNSUCCESSFUL_TEMPLATE = "Neizdevās sazvanīt klientu (tālr. nr. %s) no tālr. nr. %s %s plkst. %s";

  private final CommentBuilder commentBuilder;

  @Override
  public boolean isEventType(RetrieveCallInfoEventType eventType) {
    return RetrieveCallInfoEventType.CALL_COMPLETED.equals(eventType);
  }

  @Override
  public Mono<BtxComment> createComment(RetrieveCallInfoContext context) {
    return Mono.justOrEmpty(context.getRetrieveCallInfoCommand().getCallInfo().getDirection())
               .filter(CallDirection.OUT::equals)
               .flatMap(callDirection -> createCommentDirectionOut(context))
               .switchIfEmpty(createCommentDirectionIn(context));
  }

  private Mono<BtxComment> createCommentDirectionOut(RetrieveCallInfoContext context) {
    return Mono.justOrEmpty(context.getRetrieveCallInfoCommand())
               .filter(this::isCallSuccessful)
               .map(cmd -> buildCommentByTemplate(CALL_SUCCESSFUL_TEMPLATE, context))
               .switchIfEmpty(Mono.fromCallable(() -> buildCommentByTemplate(CALL_UNSUCCESSFUL_TEMPLATE, context)));
  }

  private Mono<BtxComment> createCommentDirectionIn(RetrieveCallInfoContext context) {
    return Mono.justOrEmpty(context.getRetrieveCallInfoCommand())
               .filter(this::isCallSuccessful)
               .map(cmd -> buildCommentByTemplate(CALL_SUCCESSFUL_TEMPLATE, context))
               .defaultIfEmpty(new BtxComment(null));
  }

  private boolean isCallSuccessful(RetrieveCallInfoCommand command) {
    var callInfo = command.getCallInfo();

    // todo: make NPE-safe (if Integer = null, equals method can throw an NPE)
    return callInfo.getCallConnectedDateTime() != null
        && !callInfo.getStatus().equals(4)
        && !callInfo.getConnectionTime().equals(0);
  }

  private BtxComment buildCommentByTemplate(String template, RetrieveCallInfoContext context) {
    return commentBuilder.buildCommentByTemplate(
        template,
        context.getRetrieveCallInfoCommand().getCallInfo().getCallEndedDateTime(),
        context.getCompanyPhoneNumber(),
        context.getStrNumber()
    );
  }
}
