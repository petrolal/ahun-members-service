package com.petrolal.ahun.ahunmembersservice.infrastructure.adapters.persistence.externalapis;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.petrolal.ahun.ahunmembersservice.application.ports.SheetsReaderPort;
import com.petrolal.ahun.ahunmembersservice.domain.dto.MemberFromSheetDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Component
public class GoogleSheetsAdapter implements SheetsReaderPort {

    @Value("${google.credentials}")
    private String googleCredentialsJson;

    private GoogleCredentials getCredentials() throws IOException {
        if (googleCredentialsJson == null || googleCredentialsJson.trim().isEmpty() || "DEFAULT_GCP".equals(googleCredentialsJson.trim())) {
            return GoogleCredentials.getApplicationDefault()
                    .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));
        }
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

    @Override
    public List<MemberFromSheetDto> readMemberSheet() {
        try {
            ValueRange response = getSheetsService()
                    .spreadsheets()
                    .values()
                    .get("1iaqNClSz0DLH1xu7SDpe-WmpL6-aZcC_Edp3TSAPlEM", "Form Responses 1!A:D")
                    .execute();

            return response.getValues()
                    .stream()
                    .skip(1)
                    .map(MemberFromSheetDto::fromRow)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}