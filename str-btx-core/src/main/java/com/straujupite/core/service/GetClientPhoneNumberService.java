package com.straujupite.core.service;

import org.springframework.stereotype.Component;

import com.straujupite.common.dto.common.callInfo.CallDirection;
import com.straujupite.common.dto.context.RetrieveCallInfoContext;

import reactor.core.publisher.Mono;

@Component
public class GetClientPhoneNumberService {

    private CompanyInfoService companyInfoService;
    public Mono<RetrieveCallInfoContext> getPhoneNumber(RetrieveCallInfoContext context) {
        return Mono.justOrEmpty(context.getRetrieveCallInfoCommand().getCallInfo().getDirection())
                .filter(CallDirection.IN::equals)
                .flatMap(direction -> getStrPhoneNumberFromDestination(context))
                .switchIfEmpty(getStrPhoneNumberFromCaller(context));
            }
    
    private Mono<RetrieveCallInfoContext> getStrPhoneNumberFromDestination(RetrieveCallInfoContext context) {
        return Mono.justOrEmpty(context)
                .map(ctx -> ctx.withStrNumber(ctx.getRetrieveCallInfoCommand().getCallInfo().getDestination().getNumber()))
                .flatMap(ctx -> companyInfoService.retrieveCompanyByPhoneNumber(context, ctx.getRetrieveCallInfoCommand().getCallInfo().getCaller().getNumber()));
    }
    private Mono<RetrieveCallInfoContext> getStrPhoneNumberFromCaller(RetrieveCallInfoContext context) {
        return Mono.justOrEmpty(context)
                .map(ctx -> ctx.withStrNumber(ctx.getRetrieveCallInfoCommand().getCallInfo().getCaller().getNumber()))
                .flatMap(ctx -> companyInfoService.retrieveCompanyByPhoneNumber(context, ctx.getRetrieveCallInfoCommand().getCallInfo().getDestination().getNumber()))
                .switchIfEmpty(getStrNumberFromDestinationOrCaller(context));
    }
    
    private Mono<RetrieveCallInfoContext> getStrNumberFromDestinationOrCaller(RetrieveCallInfoContext context) {
        return Mono.justOrEmpty(context)
                .flatMap(ctx -> companyInfoService.retrieveCompanyByPhoneNumber(context, ctx.getRetrieveCallInfoCommand().getCallInfo().getDestination().getNumber()))
                .filter(ctx -> ctx.getCompanyId() != null)
                .map(ctx -> ctx.withStrNumber(ctx.getRetrieveCallInfoCommand().getCallInfo().getCaller().getNumber()))
                .switchIfEmpty(Mono.justOrEmpty(context)
                .flatMap(ctx -> companyInfoService.retrieveCompanyByPhoneNumber(context, ctx.getRetrieveCallInfoCommand().getCallInfo().getCaller().getNumber())))
                .filter(ctx -> ctx.getCompanyId() != null)
                .map(ctx -> ctx.withStrNumber(ctx.getRetrieveCallInfoCommand().getCallInfo().getDestination().getNumber()));
            }
                
}
