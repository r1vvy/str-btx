package com.straujupite.core.service;

import com.straujupite.common.dto.context.RetrieveCallInfoContext;
import com.straujupite.core.service.addcomment.AddCommentService;
import com.straujupite.core.service.changedealstage.ChangeDealStageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CallInfoService {

  private final ClientInfoService clientInfoService;
  private final AddCommentService addCommentService;
  private final RetrieveLatestDealService retrieveLatestDealService;
  private final ChangeDealStageService changeDealStageService;

  public Mono<Void> retrieveCallInfo(RetrieveCallInfoContext context) {
    return Mono.justOrEmpty(context)
               .flatMap(clientInfoService::getByPhoneNumber)
               .filter(ctx -> ctx.getCompanyId() != null)
               .flatMap(addCommentService::addComment)
               .flatMap(ctx -> retrieveLatestDealService.retrieveLatestDealInfo(ctx,
                   String.valueOf(ctx.getCompanyId())))
               .flatMap(changeDealStageService::changeDealStageAndSetActivityIfRequired)
               .then();
  }
}
