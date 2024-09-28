package com.straujupite.itest.config;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.straujupite.itest.mock.BaseMock;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@TestConfiguration
public class TestConfig {

    @Bean
    public Clock clock() {
        return Clock.fixed(Instant.parse("2024-09-11T14:31:44Z"), ZoneId.of("UTC"));
    }

    @Bean
    @Primary
    public Sheets sheetsOutClient() throws IOException, GeneralSecurityException {
        var wireMockUrl = "http://localhost:" + BaseMock.getPort();

        return new Sheets.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                null
        ).setApplicationName("test-app")
         .setRootUrl(wireMockUrl + "/")
         .build();
    }
}
