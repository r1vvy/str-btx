package com.straujupite.out.config;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.GeneralSecurityException;


@Configuration
public class GoogleSheetsConfig {

    @Value("${spring.application.name:str-service}")
    private String applicationName;

    @Bean
    public Sheets sheetsOutClient() throws IOException, GeneralSecurityException {
        var credentials = GoogleCredentials.getApplicationDefault()
                                           .createScoped(SheetsScopes.SPREADSHEETS);
        var requestInitializer = new HttpCredentialsAdapter(credentials);

        return new Sheets.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                requestInitializer
        ).setApplicationName(applicationName)
         .build();
    }
}
