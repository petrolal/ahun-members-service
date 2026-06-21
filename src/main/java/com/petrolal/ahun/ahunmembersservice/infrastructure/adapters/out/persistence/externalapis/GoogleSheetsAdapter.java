package com.petrolal.ahun.ahunmembersservice.infrastructure.adapters.out.persistence.externalapis;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Component
public class GoogleSheetsAdapter {

    @Value("${google.credentials}")
    private String googleCredentialsJson;

    private GoogleCredentials getCredentials() throws IOException {
        return GoogleCredentials.fromStream(
                new ByteArrayInputStream(googleCredentialsJson.getBytes(StandardCharsets.UTF_8))
        ).createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));
    }

    public Sheets getSheetsService() throws IOException, GeneralSecurityException {
        return new Sheets.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(getCredentials())
        ).setApplicationName("casa-ahun-dev")
                .build();
    }
}