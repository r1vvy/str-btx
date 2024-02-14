package com.straujupite.commons.config;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class WebClientConfiguration {

    private final WebClient.Builder webClientBuilder;
    private static final String WEB_CLIENT_URL = PathConfiguration.WEB_CLIENT_URL;

    public WebClient webClient(){
        return webClientBuilder.baseUrl(WEB_CLIENT_URL).build();
    }
}
