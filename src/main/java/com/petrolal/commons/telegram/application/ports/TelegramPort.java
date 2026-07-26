package com.petrolal.commons.telegram.application.ports;

import com.petrolal.commons.telegram.domain.dto.TelegramResponseDto;

public interface TelegramPort {

    TelegramResponseDto sendMonthlyMessage();

    TelegramResponseDto sendDailyMessage();

}
