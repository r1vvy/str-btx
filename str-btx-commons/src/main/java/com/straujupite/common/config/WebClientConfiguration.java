package com.straujupite.common.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {
    @Autowired
    private ObjectMapper objectMapper;
  private final WebClientFilter webClientFilter = new WebClientFilter();

    @Value("${path.web-client-url}")
    private String url;

    @Bean
    public WebClient webClient(){
      return WebClient.builder()
                        .baseUrl(url)
                        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .codecs(configurer -> {
                          configurer.defaultCodecs()
                                    .jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper));
                          configurer.defaultCodecs()
                                    .jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper));
                        })
                      .filter(webClientFilter.logResponse())
                      .build();
    }
}
