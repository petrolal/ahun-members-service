package com.petrolal.ahun.ahunmembersservice.application.ports;

import com.petrolal.commons.telegram.domain.dto.TelegramResponseDto;

public interface TelegramPort {

    TelegramResponseDto sendMonthlyMessage();

    TelegramResponseDto sendDailyMessage();

}
