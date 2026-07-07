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
        if (googleCredentialsJson == null || googleCredentialsJson.trim().isEmpty()) {
            throw new IllegalStateException("Google credentials environment variable (GOOGLE_CREDENTIALS) is missing or empty");
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
