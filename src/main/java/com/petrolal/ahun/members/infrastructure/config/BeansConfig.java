package com.petrolal.ahun.members.infrastructure.config;

import com.petrolal.ahun.members.application.ports.GoogleSheetPort;
import com.petrolal.ahun.members.application.ports.MemberPort;
import com.petrolal.ahun.members.application.ports.MemberRepositoryPort;
import com.petrolal.ahun.members.application.ports.SheetsReaderPort;
import com.petrolal.ahun.members.application.ports.TelegramPort;
import com.petrolal.ahun.members.application.ports.TelegramSenderPort;
import com.petrolal.ahun.members.application.usecases.GoogleSheetUseCase;
import com.petrolal.ahun.members.application.usecases.MemberUseCase;
import com.petrolal.ahun.members.application.usecases.TelegramUseCases;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

  @Bean
  MemberPort memberPort(MemberRepositoryPort memberRepositoryPort) {
    return new MemberUseCase(memberRepositoryPort);
  }

  @Bean
  GoogleSheetPort googleSheetPort(
      SheetsReaderPort sheetsReaderPort, MemberRepositoryPort memberRepositoryPort) {
    return new GoogleSheetUseCase(sheetsReaderPort, memberRepositoryPort);
  }

  @Bean
  TelegramPort telegramPort(TelegramSenderPort telegramSenderPort, MemberPort memberPort) {
    return new TelegramUseCases(telegramSenderPort, memberPort);
  }
}
