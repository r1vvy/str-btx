package com.straujupite.core.service.changedealstage.flow.callcompleted.outgoing;

import static com.straujupite.common.dto.DealStage.DIDNT_PICKUP;
import static com.straujupite.common.dto.DealStage.DIDNT_PICKUP_AND_SMS;

import com.straujupite.common.dto.DealInfo;
import com.straujupite.common.dto.DealStage;
import com.straujupite.common.dto.common.callInfo.RetrieveCallInfoEventType;
import com.straujupite.common.dto.context.RetrieveCallInfoContext;
import com.straujupite.common.dto.out.request.ChangeDealStageOutRequest;
import com.straujupite.common.error.changedealstage.ActivityInfoNotFoundException;
import com.straujupite.core.service.changedealstage.flow.ChangeDealStageFlow;
import com.straujupite.core.service.changedealstage.flow.ChangeDealStageFlowBase;
import com.straujupite.out.adapter.BitrixAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientNotAnsweredLessThanThreeTimesFlow extends ChangeDealStageFlowBase implements
    ChangeDealStageFlow {

  private final BitrixAdapter bitrixAdapter;

  @Override
  public boolean isSupported(RetrieveCallInfoContext context) {
    return RetrieveCallInfoEventType.CALL_COMPLETED.equals(
        context.getRetrieveCallInfoCommand().getEventType())
        && dealIsNotCompletedAndClientNotAnsweredLessThanThreeTimes(context.getDealInfo());

  }

  @Override
  public Mono<Void> execute(RetrieveCallInfoContext context) {
    return Mono.justOrEmpty(context)
               .flatMap(this::getNextNotAnsweredStage)
               .flatMap(this::changeStage)
               .flatMap(this::getDealActivity)
               .flatMap(ctx -> updateActivityDeadlineIfPresent(ctx, 3))
               .onErrorResume(ActivityInfoNotFoundException.class,
                   e -> addNewActivityToDeal(context, 3)
               ).then();
  }

  private boolean dealIsNotCompletedAndClientNotAnsweredLessThanThreeTimes(DealInfo dealInfo) {
    return dealInfo != null
        && !COMPLETED_DEALS.contains(dealInfo.getStage())
        && !NOT_ANSWERED_MORE_THAN_OR_EQUAL_TO_THREE_TIMES_STAGE.equals(dealInfo.getStage());
  }

  private Mono<RetrieveCallInfoContext> getNextNotAnsweredStage(RetrieveCallInfoContext context) {
    return Mono.justOrEmpty(context.getDealInfo())
               .map(DealInfo::getStage)
               .map(dealStage -> switch (dealStage) {
                 case DIDNT_PICKUP -> DIDNT_PICKUP_AND_SMS;
                 case DIDNT_PICKUP_AND_SMS -> DealStage.DIDNT_PICKUP_AND_EMAIL;
                 default -> DIDNT_PICKUP;
               }).doOnNext(newStage -> log.debug("Next not answered stage: {}", newStage))
               .map(context::withNewStage)
               .defaultIfEmpty(context);
  }

  private Mono<RetrieveCallInfoContext> changeStage(RetrieveCallInfoContext context) {
    return Mono.justOrEmpty(context.getNewStage())
               .map(newStage -> buildChangeDealStageOutRequest(context, newStage))
               .doOnNext(outRequest -> log.debug("About to call ChangeDealStage: {}", outRequest))
               .flatMap(bitrixAdapter::changeStage)
               .thenReturn(context)
               .defaultIfEmpty(context);
  }

  private ChangeDealStageOutRequest buildChangeDealStageOutRequest(RetrieveCallInfoContext context,
      DealStage newStage) {
    return ChangeDealStageOutRequest.builder()
                                    .dealId(context.getDealInfo().getId())
                                    .dealStage(newStage)
                                    .build();
  }
}
