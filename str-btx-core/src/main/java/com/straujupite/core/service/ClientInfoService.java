package com.straujupite.core.service;

import static com.straujupite.common.util.ReactorMdcUtil.logOnNext;

import com.straujupite.common.dto.common.callInfo.CallDirection;
import com.straujupite.common.dto.context.RetrieveCallInfoContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientInfoService {

    private final CompanyInfoService companyInfoService;

    public Mono<RetrieveCallInfoContext> getByPhoneNumber(RetrieveCallInfoContext context) {
        return Mono.justOrEmpty(context.getRetrieveCallInfoCommand().getCallInfo().getDirection())
                   .filter(CallDirection.IN::equals)
                   .flatMap(direction -> getStrPhoneNumberFromDestination(context))
                   .switchIfEmpty(getStrNumberFromDestinationOrCaller(context));
            }

    private Mono<RetrieveCallInfoContext> getStrPhoneNumberFromDestination(RetrieveCallInfoContext context) {
        return Mono.justOrEmpty(context)
                   .map(ctx -> ctx.withStrNumber(
                       ctx.getRetrieveCallInfoCommand().getCallInfo().getDestination().getValue()))
                   .doOnEach(
                       logOnNext(ctx -> log.debug("Straujupite phone number from destination: {}",
                           ctx.getStrNumber())))
                   .flatMap(ctx -> companyInfoService.getCompanyIdByPhoneNumber(ctx,
                       ctx.getRetrieveCallInfoCommand().getCallInfo().getCaller().getValue()))
                   .map(ctx -> ctx.withCompanyPhoneNumber(
                       ctx.getRetrieveCallInfoCommand().getCallInfo().getCaller().getValue()));
    }
    private Mono<RetrieveCallInfoContext> getStrPhoneNumberFromCaller(RetrieveCallInfoContext context) {
        return Mono.justOrEmpty(context)
                   .map(ctx -> ctx.withStrNumber(
                       ctx.getRetrieveCallInfoCommand().getCallInfo().getCaller().getValue()))
                   .doOnEach(logOnNext(ctx -> log.debug("Straujupite phone number from caller: {}",
                       ctx.getStrNumber())))
                   .flatMap(ctx -> companyInfoService.getCompanyIdByPhoneNumber(ctx,
                       ctx.getRetrieveCallInfoCommand().getCallInfo().getDestination().getValue()))
                   .map(ctx -> ctx.withCompanyPhoneNumber(
                       ctx.getRetrieveCallInfoCommand().getCallInfo().getDestination().getValue()));
    }

    private Mono<RetrieveCallInfoContext> getStrNumberFromDestinationOrCaller(RetrieveCallInfoContext context) {
        return Mono.justOrEmpty(context)
                   .flatMap(this::getStrPhoneNumberFromDestination)
                   .switchIfEmpty(getStrPhoneNumberFromCaller(context))
                   .defaultIfEmpty(context);
    }
}
