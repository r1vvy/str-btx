package com.straujupite.core.service;

import org.springframework.stereotype.Component;

import com.straujupite.common.dto.BitrixError;
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
                    } else if (((CallInfo) request).getDirection().equals(CallDirection.IN)) {
                        return companyInfoService
                                .retrieveCompanyByPhoneNumber(((CallInfo) request).getCaller().getNumber());
                    } else {
                        var tempResult = companyInfoService
                                .retrieveCompanyByPhoneNumber(((CallInfo) request).getDestination().getNumber());
                        if (tempResult.equals(new com.straujupite.common.error.BitrixError("Company ID not found"))) {
                            return companyInfoService
                                    .retrieveCompanyByPhoneNumber(((CallInfo) request).getCaller().getNumber());
                        } else {
                            return tempResult;
                        }
                    }
                });

    }
}
