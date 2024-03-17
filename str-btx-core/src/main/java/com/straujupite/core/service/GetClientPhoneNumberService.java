package com.straujupite.core.service;

import org.springframework.stereotype.Component;

import com.straujupite.common.dto.GetCompanyInResponse;
import com.straujupite.common.dto.common.callInfo.CallDirection;
import com.straujupite.common.dto.common.callInfo.CallInfo;

import reactor.core.publisher.Mono;

@Component
public class GetClientPhoneNumberService {

    private CompanyInfoService companyInfoService;

    public Mono<GetCompanyInResponse> getPhoneNumber(Mono<Object> requestMono) {
        return requestMono
                .flatMap(request -> {
                    if (((CallInfo) request).getDirection().equals(CallDirection.OUT)) {
                        return companyInfoService
                                .retrieveCompanyByPhoneNumber(((CallInfo) request).getDestination().getNumber());
                    } else {
                        return companyInfoService
                                .retrieveCompanyByPhoneNumber(((CallInfo) request).getCaller().getNumber());
                    }
                });
    }
}
