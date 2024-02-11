package com.straujupite.in.converter;

import com.straujupite.common.converter.ReactiveConverter;
import com.straujupite.common.dto.common.RequestId;
import com.straujupite.common.dto.result.RetrieveCallInfoResult;
import com.straujupite.in.dto.response.CallInfoResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class RetrieveCallInfoResultToCallInfoResponseConverter implements ReactiveConverter<RetrieveCallInfoResult, CallInfoResponse> {

  @Override
  public boolean canConvert(Class<?> sourceClass, Class<?> targetClass) {
    return RetrieveCallInfoResult.class.equals(sourceClass) && CallInfoResponse.class.equals(targetClass);
  }

  @Override
  public Mono<CallInfoResponse> convert(RetrieveCallInfoResult source, Class<CallInfoResponse> targetClass) {
    return Mono.justOrEmpty(source.getRequestId())
               .map(RequestId::toString)
               .flatMap(this::buildResponse);
  }

  private Mono<CallInfoResponse> buildResponse(String requestId) {
    return Mono.just(CallInfoResponse.builder()
                                     .responseId(requestId)
                                     .build());
  }
}
