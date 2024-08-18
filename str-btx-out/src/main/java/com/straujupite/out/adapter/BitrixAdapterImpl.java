package com.straujupite.out.adapter;

import static com.straujupite.common.util.ReactorMdcUtil.logOnNext;

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
import com.straujupite.common.util.uriformatter.UriBuilderRegistry;
import com.straujupite.common.webclient.WebClientService;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;

@Service
@RequiredArgsConstructor
@Slf4j
public class BitrixAdapterImpl implements BitrixAdapter {

  private final UriBuilderRegistry uriBuilderRegistry;
  private final WebClientService webClientService;

  @Override
  public Mono<Void> addActivity(AddActivityOutRequest outRequest) {
    return get(outRequest, Void.class);
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
    return get(outRequest, GetActivityOutResponse.class);
  }

  @Override
  public Mono<GetCompanyOutResponse> retrieveCompanyIdByPhoneNumber(PhoneNumber phoneNumber) {
    return get(phoneNumber, GetCompanyOutResponse.class);
  }

  @Override
  public Mono<GetCompanyDealsOutResponse> retrieveDealsByCompanyId(CompanyId companyId) {
    return get(companyId, GetCompanyDealsOutResponse.class);
  }

  @Override
  public Mono<UpdateActivityOutResponse> updateActivityDeadline(
      UpdateActivityDeadlineOutRequest request) {
    return get(request, UpdateActivityOutResponse.class);
  }

  private <Request, Response> Mono<Response> get(Request request, Class<Response> responseClass) {
    return Mono.fromCallable(() -> uriBuilderRegistry.getUriBuilder(request.getClass()))
               .map(builder -> builder.buildUri(request))
               .flatMap(uri -> webClientService.getAndReceiveMono(uri, responseClass))
               .doOnEach(logResponse());
  }

  private <Request, Response> Mono<Response> post(String uri, Request request,
                                                  Class<Response> responseClass) {
    return webClientService.postAndReceiveMono(uri, request, responseClass)
                           .doOnEach(logResponse());
  }

  private <Response> Consumer<Signal<Response>> logResponse() {
    return logOnNext(response -> log.info("Response from Bitrix: {}", response));
  }
}
