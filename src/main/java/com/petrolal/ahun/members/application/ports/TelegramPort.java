package com.petrolal.ahun.members.application.ports;

import com.petrolal.ahun.members.domain.dto.TelegramResponseDto;

public interface TelegramPort {

  TelegramResponseDto sendMonthlyMessage();

  TelegramResponseDto sendDailyMessage();
}
