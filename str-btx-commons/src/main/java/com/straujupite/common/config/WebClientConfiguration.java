package com.straujupite.common.config;



import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class WebClientConfiguration {
    private static final String WEB_CLIENT_URL = PathConfiguration.WEB_CLIENT_URL;

    @Bean
    public WebClient webClient(){return WebClient.builder().baseUrl(WEB_CLIENT_URL).build();}
}
