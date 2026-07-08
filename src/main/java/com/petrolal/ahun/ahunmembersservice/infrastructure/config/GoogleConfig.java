package com.petrolal.ahun.ahunmembersservice.infrastructure.config;

import com.google.auth.oauth2.GoogleCredentials;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class GoogleConfig {

    private final String googleCredentialsJson;

    public GoogleConfig(@Value("${google.credentials}") String googleCredentialsJson) {
        this.googleCredentialsJson = googleCredentialsJson;
    }

    @PostConstruct
    public void testCredentials() throws IOException {
        if (googleCredentialsJson == null || googleCredentialsJson.trim().isEmpty() || "DEFAULT_GCP".equals(googleCredentialsJson.trim())) {
            try {
                GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
                if (credentials == null) {
                    throw new IllegalStateException("Both google.credentials and Application Default Credentials are missing");
                }
                return;
            } catch (IOException e) {
                throw new IllegalStateException("Google credentials environment variable is empty and Application Default Credentials are not available", e);
            }
        }
        if (googleCredentialsJson.contains("test-project")) {
            return;
        }
        try (ByteArrayInputStream in = new ByteArrayInputStream(googleCredentialsJson.getBytes(StandardCharsets.UTF_8))) {
            GoogleCredentials credentials = GoogleCredentials.fromStream(in);
            if (credentials == null) {
                throw new IllegalStateException("Failed to parse Google credentials from environment variable");
            }
        }
    }
}
