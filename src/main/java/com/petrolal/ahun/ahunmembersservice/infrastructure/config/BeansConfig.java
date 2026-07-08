package com.petrolal.ahun.ahunmembersservice.infrastructure.config;

import com.petrolal.ahun.ahunmembersservice.application.ports.GoogleSheetPort;
import com.petrolal.ahun.ahunmembersservice.application.ports.MemberPort;
import com.petrolal.ahun.ahunmembersservice.application.ports.MemberRepositoryPort;
import com.petrolal.ahun.ahunmembersservice.application.ports.SheetsReaderPort;
import com.petrolal.ahun.ahunmembersservice.application.ports.TelegramPort;
import com.petrolal.ahun.ahunmembersservice.application.ports.TelegramSenderPort;
import com.petrolal.ahun.ahunmembersservice.application.usecases.GoogleSheetUseCase;
import com.petrolal.ahun.ahunmembersservice.application.usecases.MemberUseCase;
import com.petrolal.ahun.ahunmembersservice.application.usecases.TelegramUseCases;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Bean
    MemberPort memberPort(MemberRepositoryPort memberRepositoryPort) {
        return new MemberUseCase(memberRepositoryPort);
    }

    @Bean
    GoogleSheetPort googleSheetPort(SheetsReaderPort sheetsReaderPort,
                                    MemberRepositoryPort memberRepositoryPort) {
        return new GoogleSheetUseCase(sheetsReaderPort, memberRepositoryPort);
    }

    @Bean
    TelegramPort telegramPort(TelegramSenderPort telegramSenderPort, MemberPort memberPort) {
        return new TelegramUseCases(telegramSenderPort, memberPort);
    }
}
