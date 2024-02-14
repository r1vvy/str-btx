package com.straujupite.out.adapter;


import com.straujupite.commons.config.WebClientConfiguration;

import com.straujupite.commons.dto.GetCompanyInResponse;
import org.junit.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertEquals;

public class RetrieveCompanyInfoTest {

    private final WebClientConfiguration webClientConfiguration = new WebClientConfiguration(WebClient.builder());

    private final RetrieveCompanyInfoAdapter retrieveCompanyInfoAdapter = new RetrieveCompanyInfoAdapter(webClientConfiguration);
    @Test
    public void retrieveCompanyWithExistingNumber(){
        Mono<GetCompanyInResponse> result = retrieveCompanyInfoAdapter.retrieveCompanyByPhoneNumber("+37129341792");
        assertEquals(result.block().getCompanyID(), "2930");
    }

    @Test
    public void retrieveCompanyWithWrongNumber() {
        Mono<GetCompanyInResponse> result = retrieveCompanyInfoAdapter.retrieveCompanyByPhoneNumber("+371293417792");
        assertEquals(result.block().getCompanyID(), "0");
    }
}
