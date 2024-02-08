package com.straujupite.commons.utility;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class WebClientConfiguration {
    
    private final WebClient.Builder webClientBuilder;

    public WebClient webClient(){
        return webClientBuilder.baseUrl("https://straujupte.bitrix24.eu/rest/24/cf6tkfj4yja3spsr/").build();
    }
}
