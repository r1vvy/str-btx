package com.straujupite.out.adapter;

import com.straujupite.common.dto.common.PhoneNumber;
import com.straujupite.common.dto.common.bitrix.BitrixEndpoints;
import com.straujupite.common.dto.common.bitrix.CompanyId;
import com.straujupite.common.dto.out.command.AddCommentOutCommand;
import com.straujupite.common.dto.out.request.AddActivityOutRequest;
import com.straujupite.common.dto.out.request.ChangeDealStageOutRequest;
import com.straujupite.common.dto.out.request.GetActivityOutRequest;
import com.straujupite.common.dto.out.request.UpdateActivityDeadlineOutRequest;
import com.straujupite.common.dto.out.response.GetActivityOutResponse;
import com.straujupite.common.dto.out.response.GetCompanyDealsOutResponse;
import com.straujupite.common.dto.out.response.GetCompanyOutResponse;
import com.straujupite.common.dto.out.response.UpdateActivityOutResponse;
import com.straujupite.common.util.uriformatter.UriBuilder;
import com.straujupite.common.webclient.WebClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class BitrixAdapterImpl implements BitrixAdapter {

  private final UriBuilder<AddActivityOutRequest> addActivityOutRequestUriBuilder;
  private final UriBuilder<PhoneNumber> getCompanyInfoByPhoneNumberUriBruilder;
  private final UriBuilder<GetActivityOutRequest> getNotCompletedActivitiesUriBuilder;
  private final UriBuilder<CompanyId> getNotCompletedDealsByCompanyIdUriBuilder;
  private final UriBuilder<UpdateActivityDeadlineOutRequest> updateActivityDeadlineUriBuilder;

  private final WebClientService webClientService;

  @Override
  public Mono<Void> addActivity(AddActivityOutRequest outRequest) {
    return get(addActivityOutRequestUriBuilder, outRequest, Void.class);
  }

  @Override
  public Mono<Void> addComment(AddCommentOutCommand command) {
    return post(BitrixEndpoints.ADD_COMMENT, command, Void.class);
  }

  @Override
  public Mono<Void> changeStage(ChangeDealStageOutRequest outRequest) {
    return post(BitrixEndpoints.UPDATE_DEAL, outRequest, Void.class);
  }

  @Override
  public Mono<GetActivityOutResponse> getActivity(GetActivityOutRequest outRequest) {
    return get(getNotCompletedActivitiesUriBuilder, outRequest, GetActivityOutResponse.class);
  }

  @Override
  public Mono<GetCompanyOutResponse> retrieveCompanyIdByPhoneNumber(PhoneNumber phoneNumber) {
    return get(getCompanyInfoByPhoneNumberUriBruilder, phoneNumber, GetCompanyOutResponse.class);
  }

  @Override
  public Mono<GetCompanyDealsOutResponse> retrieveDealsByCompanyId(CompanyId companyId) {
    return get(getNotCompletedDealsByCompanyIdUriBuilder, companyId,
        GetCompanyDealsOutResponse.class);
  }

  @Override
  public Mono<UpdateActivityOutResponse> updateActivityDeadline(
      UpdateActivityDeadlineOutRequest request) {
    return get(updateActivityDeadlineUriBuilder, request, UpdateActivityOutResponse.class);
  }

  private <Request, Response> Mono<Response> get(UriBuilder<Request> builder, Request request,
      Class<Response> responseClass) {
    return Mono.fromCallable(() -> builder.buildUri(request))
               .flatMap(uri -> webClientService.getAndReceiveMono(uri, responseClass))
               .doOnNext(response -> log.debug("Response from Bitrix: {}", response));
  }

  private <Request, Response> Mono<Response> post(String uri, Request request,
      Class<Response> responseClass) {
    return webClientService.postAndReceiveMono(uri, request, responseClass);
  }
}
