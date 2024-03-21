package com.straujupite.core.service.addcomment;


import com.straujupite.common.dto.common.bitrix.BtxComment;
import com.straujupite.common.dto.common.bitrix.EntityType;
import com.straujupite.common.dto.common.callInfo.RetrieveCallInfoEventType;
import com.straujupite.common.dto.context.RetrieveCallInfoContext;
import com.straujupite.common.dto.out.command.AddCommentOutCommand;
import com.straujupite.out.adapter.AddCommentOutAdapter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AddCommentService {

  @Autowired
  private List<AddCommentEventTypeFlow> eventFlows;

  private final AddCommentOutAdapter addCommentAdapter;

  public Mono<RetrieveCallInfoContext> addComment(RetrieveCallInfoContext context) {
    return Mono.fromSupplier(() -> findFlow(context.getRetrieveCallInfoCommand().getEventType()))
               .flatMap(eventFlow -> eventFlow.createComment(context))
               .map(comment -> createOutCommand(context, comment))
               .flatMap(addCommentAdapter::addComment)
               .thenReturn(context);
  }

  // todo: add specific error after handling is planned
  private AddCommentEventTypeFlow findFlow(RetrieveCallInfoEventType retrieveCallInfoEventType) {
    return eventFlows.stream()
                     .filter(flow -> flow.isEventType(retrieveCallInfoEventType))
                     .findFirst()
                     .orElseThrow(() -> new RuntimeException("Couldn't find supported flow"));
  }
  private AddCommentOutCommand createOutCommand(RetrieveCallInfoContext context, BtxComment comment) {
    return AddCommentOutCommand.builder()
                               .entityId(context.getCompanyId())
                               .entityType(EntityType.COMPANY)
                               .comment(comment)
                               .build();
  }

}
