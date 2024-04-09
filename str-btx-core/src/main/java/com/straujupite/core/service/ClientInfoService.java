package com.straujupite.core.service;

import com.straujupite.common.dto.common.callInfo.CallDirection;
import com.straujupite.common.dto.context.RetrieveCallInfoContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ClientInfoService {

    private final CompanyInfoService companyInfoService;

    public Mono<RetrieveCallInfoContext> getByPhoneNumber(RetrieveCallInfoContext context) {
        return Mono.justOrEmpty(context.getRetrieveCallInfoCommand().getCallInfo().getDirection())
                   .filter(CallDirection.IN::equals)
                   .flatMap(direction -> getStrPhoneNumberFromDestination(context))
                   .switchIfEmpty(getStrPhoneNumberFromCaller(context));
            }

    private Mono<RetrieveCallInfoContext> getStrPhoneNumberFromDestination(RetrieveCallInfoContext context) {
        return Mono.justOrEmpty(context)
                   .map(ctx -> ctx.withStrNumber(
                       ctx.getRetrieveCallInfoCommand().getCallInfo().getDestination().getNumber()))
                   .flatMap(ctx -> companyInfoService.getCompanyIdByPhoneNumber(ctx,
                       ctx.getRetrieveCallInfoCommand().getCallInfo().getCaller().getNumber()));
    }
    private Mono<RetrieveCallInfoContext> getStrPhoneNumberFromCaller(RetrieveCallInfoContext context) {
        return Mono.justOrEmpty(context)
                   .map(ctx -> ctx.withStrNumber(
                       ctx.getRetrieveCallInfoCommand().getCallInfo().getCaller().getNumber()))
                   .flatMap(ctx -> companyInfoService.getCompanyIdByPhoneNumber(ctx,
                       ctx.getRetrieveCallInfoCommand().getCallInfo().getDestination().getNumber()))
                   .switchIfEmpty(getStrNumberFromDestinationOrCaller(context));
    }

    private Mono<RetrieveCallInfoContext> getStrNumberFromDestinationOrCaller(RetrieveCallInfoContext context) {
        return Mono.justOrEmpty(context)
                   .flatMap(ctx -> companyInfoService.getCompanyIdByPhoneNumber(context,
                       ctx.getRetrieveCallInfoCommand().getCallInfo().getDestination().getNumber()))
                   .filter(ctx -> ctx.getCompanyId() != null)
                   .map(ctx -> ctx.withStrNumber(
                       ctx.getRetrieveCallInfoCommand().getCallInfo().getCaller().getNumber()))
                   .switchIfEmpty(companyInfoService.getCompanyIdByPhoneNumber(context,
                       context.getRetrieveCallInfoCommand().getCallInfo().getCaller().getNumber()))
                   .filter(ctx -> ctx.getCompanyId() != null)
                   .map(ctx -> ctx.withStrNumber(
                       ctx.getRetrieveCallInfoCommand().getCallInfo().getDestination()
                          .getNumber()));
    }

                
}
