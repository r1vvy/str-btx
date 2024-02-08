package com.straujupite.core.service;

import com.straujupite.commons.dto.GetCompanyInResponse;
import com.straujupite.commons.utility.WebClientConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CompanyInfoService {

    private final WebClientConfiguration webClient;
    public Mono<GetCompanyInResponse> retrieveCompanyByPhoneNumber(String phoneNumber){
        return webClient.webClient().get()
                .uri("crm.duplicate.findbycomm.json?entity_type=COMPANY&type=PHONE&values[]=" + phoneNumber)
                .retrieve()
                .bodyToMono(GetCompanyInResponse.class);
    }
}
