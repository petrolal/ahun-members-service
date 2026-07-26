package com.petrolal.commons.telegram.infrastructure.config;

import com.petrolal.commons.telegram.application.ports.GoogleSheetPort;
import com.petrolal.commons.telegram.application.ports.MemberPort;
import com.petrolal.commons.telegram.application.ports.MemberRepositoryPort;
import com.petrolal.commons.telegram.application.ports.SheetsReaderPort;
import com.petrolal.commons.telegram.application.ports.TelegramPort;
import com.petrolal.commons.telegram.application.ports.TelegramSenderPort;
import com.petrolal.commons.telegram.application.usecases.GoogleSheetUseCase;
import com.petrolal.commons.telegram.application.usecases.MemberUseCase;
import com.petrolal.commons.telegram.application.usecases.TelegramUseCases;
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
