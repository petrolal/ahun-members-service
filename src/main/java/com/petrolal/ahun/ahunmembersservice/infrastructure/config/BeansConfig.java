package com.petrolal.ahun.ahunmembersservice.infrastructure.config;

import com.petrolal.ahun.ahunmembersservice.application.ports.GoogleSheetPort;
import com.petrolal.ahun.ahunmembersservice.application.ports.MemberPort;
import com.petrolal.ahun.ahunmembersservice.application.ports.MemberRepositoryPort;
import com.petrolal.ahun.ahunmembersservice.application.usecases.GoogleSheetUseCase;
import com.petrolal.ahun.ahunmembersservice.application.usecases.MemberUseCase;
import com.petrolal.ahun.ahunmembersservice.infrastructure.adapters.out.persistence.google.GoogleSheetsAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Bean
    MemberPort memberPort(MemberRepositoryPort memberRepositoryPort) {
        return new MemberUseCase(memberRepositoryPort);
    }

    @Bean
    GoogleSheetPort googleSheetPort(GoogleSheetsAdapter googleSheetsAdapter,
                                    MemberRepositoryPort memberRepositoryPort) {
        return new GoogleSheetUseCase(googleSheetsAdapter, memberRepositoryPort);
    }
}
