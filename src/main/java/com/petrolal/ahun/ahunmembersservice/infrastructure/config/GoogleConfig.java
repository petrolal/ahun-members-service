package com.petrolal.ahun.ahunmembersservice.infrastructure.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class GoogleConfig {

    private final ResourceLoader resourceLoader;

    public GoogleConfig(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    public void testCredentialsFile() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:credentials/credentials.json");
        try (InputStream in = resource.getInputStream()) {
            if (in.read() == -1) {
                throw new IllegalStateException("credentials.json is empty");
            }
        }
    }
}
