package com.straujupite.core.service.changedealstage.flow.callcompleted.outgoing;

import com.straujupite.common.dto.DealInfo;
import com.straujupite.common.dto.common.callInfo.RetrieveCallInfoEventType;
import com.straujupite.common.dto.context.ChangeDealStageContext;
import com.straujupite.common.error.changedealstage.ActivityInfoNotFoundException;
import com.straujupite.core.service.changedealstage.flow.ChangeDealStageFlow;
import com.straujupite.core.service.changedealstage.flow.ChangeDealStageFlowBase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ClientNotAnsweredMoreOrEqualToThreeTimes extends ChangeDealStageFlowBase implements
                                                                                      ChangeDealStageFlow {

  @Override
  public boolean isSupported(ChangeDealStageContext context) {
    return RetrieveCallInfoEventType.CALL_COMPLETED.equals(context.getEventType())
        && dealIsNotCompletedAndClientNotAnsweredMoreOrEqualToThreeTimes(context.getDealInfo());
  }

  @Override
  public Mono<Void> execute(ChangeDealStageContext context) {
    return Mono.justOrEmpty(context)
               .flatMap(this::getDealActivity)
               .flatMap(ctx -> updateActivityDeadlineIfPresent(ctx, 7))
               .onErrorResume(ActivityInfoNotFoundException.class,
                   e -> addNewActivityToDeal(context, 7)
               ).then();
  }

  private boolean dealIsNotCompletedAndClientNotAnsweredMoreOrEqualToThreeTimes(DealInfo dealInfo) {
    return dealInfo != null
        && !COMPLETED_DEALS.contains(dealInfo.getStage())
        && NOT_ANSWERED_MORE_THAN_OR_EQUAL_TO_THREE_TIMES_STAGE.equals(dealInfo.getStage());
  }
}
