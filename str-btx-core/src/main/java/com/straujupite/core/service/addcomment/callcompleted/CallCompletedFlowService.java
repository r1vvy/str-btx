package com.straujupite.core.service.addcomment.callcompleted;

import static com.straujupite.common.dto.common.callInfo.CallDirection.OUT;
import static com.straujupite.common.util.ReactorMdcUtil.logOnNext;

import com.straujupite.common.dto.common.bitrix.BtxComment;
import com.straujupite.common.dto.common.callInfo.CallDirection;
import com.straujupite.common.dto.common.callInfo.RetrieveCallInfoEventType;
import com.straujupite.common.dto.context.RetrieveCallInfoContext;
import com.straujupite.common.dto.in.command.RetrieveCallInfoCommand;
import com.straujupite.common.util.addcomment.CommentBuilder;
import com.straujupite.core.service.addcomment.AddCommentEventTypeFlow;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class CallCompletedFlowService implements AddCommentEventTypeFlow {

  private static final String CALL_SUCCESSFUL_TEMPLATE = "✅ Zvans ar klientu (tālr. nr. %s) no tālr. nr. %s. pabeigts. %s plkst. %s";
  private static final String CALL_UNSUCCESSFUL_TEMPLATE = "❌ Neizdevās sazvanīt klientu (tālr. nr. %s) no tālr. nr. %s %s plkst. %s";

  private final CommentBuilder commentBuilder;

  @Override
  public boolean isEventType(RetrieveCallInfoEventType eventType) {
    return RetrieveCallInfoEventType.CALL_COMPLETED.equals(eventType);
  }

  @Override
  public Mono<BtxComment> createComment(RetrieveCallInfoContext context) {
    return Mono.justOrEmpty(context.getRetrieveCallInfoCommand().getCallInfo().getDirection())
               .filter(OUT::equals)
               .flatMap(callDirection -> createCommentDirectionOut(context))
               .switchIfEmpty(createCommentDirectionIn(context));
  }

  private Mono<BtxComment> createCommentDirectionOut(RetrieveCallInfoContext context) {
    return Mono.justOrEmpty(context.getRetrieveCallInfoCommand())
               .filter(this::isCallSuccessful)
               .map(cmd -> buildCommentByTemplate(CALL_SUCCESSFUL_TEMPLATE, context))
               .map(comment -> addCallDirectionPrefix(comment, CallDirection.OUT))
               .doOnEach(logOnNext(comment -> log.debug("Created comment: {}", comment)))
               .switchIfEmpty(Mono.fromCallable(() -> buildCommentByTemplate(CALL_UNSUCCESSFUL_TEMPLATE, context)));
  }

  private Mono<BtxComment> createCommentDirectionIn(RetrieveCallInfoContext context) {
    return Mono.justOrEmpty(context.getRetrieveCallInfoCommand())
               .filter(this::isCallSuccessful)
               .map(cmd -> buildCommentByTemplate(CALL_SUCCESSFUL_TEMPLATE, context))
               .map(comment -> addCallDirectionPrefix(comment, CallDirection.IN))
               .doOnEach(logOnNext(comment -> log.debug("Created comment: {}", comment)));
  }

  private boolean isCallSuccessful(RetrieveCallInfoCommand command) {
    var callInfo = command.getCallInfo();

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

  private BtxComment addCallDirectionPrefix(BtxComment comment, CallDirection direction) {
    return CallDirection.OUT == direction
        ? new BtxComment(comment.getValue().concat("IZEJOŠS ZVANS: "))
        : new BtxComment(comment.getValue().concat("IENĀKOŠS ZVANS: "));
  }
}
