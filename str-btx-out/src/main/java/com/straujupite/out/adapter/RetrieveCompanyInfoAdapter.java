package com.straujupite.out.adapter;

import com.straujupite.commons.dto.GetCompanyInResponse;
import com.straujupite.commons.config.WebClientConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RetrieveCompanyInfoAdapter {

    private final WebClientConfiguration webClient;
    private static final String URI = "crm.duplicate.findbycomm.json?entity_type=COMPANY&type=PHONE&values[]=";
    public Mono<GetCompanyInResponse> retrieveCompanyByPhoneNumber(String phoneNumber){
        return webClient.webClient().get()
                                    .uri(URI + phoneNumber)
                                    .retrieve()
                                    .bodyToMono(GetCompanyInResponse.class);

    }
}
