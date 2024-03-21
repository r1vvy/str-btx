package com.straujupite.core.service;

import org.springframework.stereotype.Component;

import com.straujupite.common.dto.common.callInfo.CallDirection;
import com.straujupite.common.dto.common.callInfo.CallInfo;
import com.straujupite.common.dto.context.RetrieveCallInfoContext;

import reactor.core.publisher.Mono;

@Component
public class GetClientPhoneNumberService {

    private CompanyInfoService companyInfoService;

    public Mono<RetrieveCallInfoContext> getPhoneNumber(RetrieveCallInfoContext context) {
        
        CallInfo callInfo = context.getRetrieveCallInfoCommand().getCallInfo();
        CallDirection direction = callInfo.getDirection();
        String destinationNumber = callInfo.getDestination().getNumber();
        String callerNumber = callInfo.getCaller().getNumber();

        if (context.getRetrieveCallInfoCommand().getEventType().toString().startsWith("LOST_")) {
            companyInfoService.retrieveCompanyByPhoneNumber(context, destinationNumber);
            if (context.getCompanyId() == null) {
                context.withStrNumber(destinationNumber);
                companyInfoService.retrieveCompanyByPhoneNumber(context, callerNumber);
            }
            context.withStrNumber(callerNumber);
        }
        
        if (direction == CallDirection.OUT) {
            context.withStrNumber(callerNumber);
            return companyInfoService.retrieveCompanyByPhoneNumber(context, destinationNumber);
        }
        
        if (direction == CallDirection.IN) {
            context.withStrNumber(destinationNumber);
            return companyInfoService.retrieveCompanyByPhoneNumber(context, callerNumber);
        }
        
        return Mono.just(context);
    }
}
