package com.petrolal.ahun.ahunmembersservice.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Ahun Members Service")
                        .version("1.0")
                        .description("Auto-generated Documentation"))
                .servers(List.of(new Server().url("/").description("Default Server URL")));
    }
}
