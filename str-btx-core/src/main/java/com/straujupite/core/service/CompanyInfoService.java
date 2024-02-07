package com.straujupite.core.service;

import com.straujupite.commons.WebClientConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CompanyInfoService {

    private final WebClientConfiguration webClient;
    public Mono<Object> retrieveCompanyByPhoneNumber(String phoneNumber){
        return webClient.webClient().get()
                .uri("crm.duplicate.findbycomm.json?entity_type=COMPANY&type=PHONE&values[]=" + phoneNumber)
                .retrieve()
                .bodyToMono(Object.class);
    }
}
