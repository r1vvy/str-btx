package com.straujupite.core.service.changedealstage;

import com.straujupite.common.dto.context.RetrieveCallInfoContext;
import com.straujupite.common.error.changedealstage.FlowNotFoundException;
import com.straujupite.core.service.changedealstage.flow.ChangeDealStageFlow;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ChangeDealStageService {

  @Autowired
  private List<ChangeDealStageFlow> eventFlows;

  public Mono<RetrieveCallInfoContext> changeDealStageAndSetActivityIfRequired(
      RetrieveCallInfoContext context) {
    return Mono.justOrEmpty(context)
               .map(this::findFlow)
               .flatMap(eventTypeFlow -> eventTypeFlow.execute(context))
               .thenReturn(context)
               .onErrorResume(FlowNotFoundException.class, err -> Mono.just(context));
  }

  private ChangeDealStageFlow findFlow(RetrieveCallInfoContext context) {
    return eventFlows.stream()
                     .filter(flow -> flow.isSupported(context))
                     .findFirst()
                     .orElseThrow(() -> new FlowNotFoundException("Couldn't find supported flow"));
  }

}
