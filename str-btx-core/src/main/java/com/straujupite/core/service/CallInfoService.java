package com.straujupite.core.service;

import static com.straujupite.common.util.ReactorMdcUtil.logOnNext;

import com.straujupite.common.dto.DealInfo;
import com.straujupite.common.dto.common.callInfo.RetrieveCallInfoEventType;
import com.straujupite.common.dto.context.RetrieveCallInfoContext;
import com.straujupite.common.dto.in.command.RetrieveCallInfoCommand;
import com.straujupite.core.service.addcomment.AddCommentService;
import com.straujupite.core.service.changedealstage.ChangeDealStageService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class CallInfoService {

  private static final List<RetrieveCallInfoEventType> IGNORED_EVENTS = List.of(
      RetrieveCallInfoEventType.CALL_CONNECTED,
      RetrieveCallInfoEventType.CALL_STARTED,
      RetrieveCallInfoEventType.LOST_CALLER_UPDATED
  );

  private final ClientInfoService clientInfoService;
  private final AddCommentService addCommentService;
  private final RetrieveDealService retrieveDealService;
  private final ChangeDealStageService changeDealStageService;

  public Mono<Void> retrieveCallInfo(RetrieveCallInfoContext context) {
    return Mono.justOrEmpty(context)
               .filter(ctx -> isNotIgnoredEvent(ctx.getRetrieveCallInfoCommand()))
               .flatMap(clientInfoService::getByPhoneNumber)
               .filter(ctx -> ctx.getCompanyId() != null)
               .doOnEach(logOnNext(ctx -> log.info("Company ID is not null, continuing flow")))
               .flatMap(addCommentService::addComment)
               .flatMap(ctx -> retrieveDealService.retrieveAllDealsByCompanyId(ctx, String.valueOf(
                   ctx.getCompanyId())))
               .filter(ctx -> ctx.getDeals() != null && !ctx.getDeals()
                                                            .isEmpty())
               .flatMap(this::executeDealStageChangeFlowIfRequired);
  }

  private Mono<Void> executeDealStageChangeFlowIfRequired(RetrieveCallInfoContext context) {
    return Mono.justOrEmpty(context)
               .flatMapMany(ctx -> Flux.fromIterable(ctx.getDeals()))
               .flatMap(deal -> changeDealStageAndSetActivityIfRequired(context, deal))
               .then();
  }

  private Mono<RetrieveCallInfoContext> changeDealStageAndSetActivityIfRequired(
      RetrieveCallInfoContext context, DealInfo deal) {
    return changeDealStageService.changeDealStageAndSetActivityIfRequired(context, deal)
                                 .onErrorResume(err -> Mono.empty());
  }

  private boolean isNotIgnoredEvent(RetrieveCallInfoCommand command) {
    return !IGNORED_EVENTS.contains(command.getEventType());
  }
}
