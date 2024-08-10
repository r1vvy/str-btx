package com.straujupite.core.service.changedealstage;

import static com.straujupite.common.util.ReactorMdcUtil.logOnError;
import static com.straujupite.common.util.ReactorMdcUtil.logOnNext;

import com.straujupite.common.dto.DealInfo;
import com.straujupite.common.dto.context.ChangeDealStageContext;
import com.straujupite.common.dto.context.RetrieveCallInfoContext;
import com.straujupite.common.error.changedealstage.FlowNotFoundException;
import com.straujupite.core.service.changedealstage.flow.ChangeDealStageFlow;
import java.util.List;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChangeDealStageService {

  @Autowired
  private List<ChangeDealStageFlow> eventFlows;

  public Mono<RetrieveCallInfoContext> changeDealStageAndSetActivityIfRequired(
      RetrieveCallInfoContext context, DealInfo dealInfo) {
    return Mono.justOrEmpty(context)
               .map(convertToChangeDealStageContext(dealInfo))
               .doOnEach(logOnNext(ctx -> log.debug("ChangeDealStageContext created: {}", ctx)))
               .flatMap(this::findAndExecuteFlow)
               .thenReturn(context)
               .onErrorResume(FlowNotFoundException.class, err -> Mono.just(context));
  }

  private Function<RetrieveCallInfoContext, ChangeDealStageContext> convertToChangeDealStageContext(
      DealInfo dealInfo) {
    return ctx -> ChangeDealStageContext.builder()
                                        .dealInfo(dealInfo)
                                        .eventType(ctx.getRetrieveCallInfoCommand()
                                                      .getEventType())
                                        .build();
  }

  private Mono<Void> findAndExecuteFlow(ChangeDealStageContext context) {
    return Mono.justOrEmpty(context)
               .map(this::findFlow)
               .doOnEach(logOnError(
                   err -> log.error("Error while finding flow: {}, message: {}", context,
                                    err.getMessage())))
               .flatMap(flow -> flow.execute(context));
  }

  private ChangeDealStageFlow findFlow(ChangeDealStageContext context) {
    return eventFlows.stream()
                     .filter(flow -> flow.isSupported(context))
                     .findFirst()
                     .orElseThrow(() -> new FlowNotFoundException("Couldn't find supported flow"));
  }

}
