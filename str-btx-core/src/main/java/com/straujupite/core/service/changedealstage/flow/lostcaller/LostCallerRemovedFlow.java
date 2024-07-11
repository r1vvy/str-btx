package com.straujupite.core.service.changedealstage.flow.lostcaller;

import static com.straujupite.common.util.ReactorMdcUtil.logOnNext;

import com.straujupite.common.dto.DealStage;
import com.straujupite.common.dto.common.callInfo.RetrieveCallInfoEventType;
import com.straujupite.common.dto.context.RetrieveCallInfoContext;
import com.straujupite.common.dto.out.request.ChangeDealStageOutRequest;
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
public class LostCallerRemovedFlow extends ChangeDealStageFlowBase implements ChangeDealStageFlow {

  private final BitrixAdapter bitrixAdapter;

  @Override
  public boolean isSupported(RetrieveCallInfoContext context) {
    return RetrieveCallInfoEventType.LOST_CALLER_REMOVED.equals(
        context.getRetrieveCallInfoCommand().getEventType());
  }

  @Override
  public Mono<Void> execute(RetrieveCallInfoContext context) {
    return Mono.fromSupplier(context.getDealInfo()::getId)
               .map(this::buildChangeDealStageOutRequest)
               .doOnEach(logOnNext(
                   outRequest -> log.info("About to call ChangeDealStage: {}", outRequest)))
               .flatMap(bitrixAdapter::changeStage)
               .then();
  }

  private ChangeDealStageOutRequest buildChangeDealStageOutRequest(String dealId) {
    return ChangeDealStageOutRequest.builder()
                                    .dealId(dealId)
                                    .dealStage(DealStage.IN_PROCESS)
                                    .build();
  }
}
