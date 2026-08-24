package com.petrolal.ahun.members.application.ports;

import com.petrolal.ahun.members.domain.dto.TelegramResponseDto;

public interface TelegramSenderPort {
    TelegramResponseDto sendNotification(String message);
}
