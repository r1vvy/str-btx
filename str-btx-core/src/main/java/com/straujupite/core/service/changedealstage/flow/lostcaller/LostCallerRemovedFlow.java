package com.straujupite.core.service.changedealstage.flow.lostcaller;

import com.straujupite.common.dto.DealStage;
import com.straujupite.common.dto.common.callInfo.RetrieveCallInfoEventType;
import com.straujupite.common.dto.context.RetrieveCallInfoContext;
import com.straujupite.common.dto.out.request.ChangeDealStageOutRequest;
import com.straujupite.core.service.changedealstage.flow.ChangeDealStageFlow;
import com.straujupite.core.service.changedealstage.flow.ChangeDealStageFlowBase;
import com.straujupite.out.adapter.ChangeDealStageAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class LostCallerRemovedFlow extends ChangeDealStageFlowBase implements ChangeDealStageFlow {

  private final ChangeDealStageAdapter changeDealStageAdapter;

  @Override
  public boolean isSupported(RetrieveCallInfoContext context) {
    return RetrieveCallInfoEventType.LOST_CALLER_REMOVED.equals(
        context.getRetrieveCallInfoCommand().getEventType());
  }

  @Override
  public Mono<Void> execute(RetrieveCallInfoContext context) {
    return Mono.fromSupplier(context.getDealInfo()::getId)
               .map(this::buildChangeDealStageOutRequest)
               .flatMap(changeDealStageAdapter::changeStage)
               .then();
  }

  private ChangeDealStageOutRequest buildChangeDealStageOutRequest(String dealId) {
    return ChangeDealStageOutRequest.builder()
                                    .dealId(dealId)
                                    .dealStage(DealStage.IN_PROCESS)
                                    .build();
  }
}
