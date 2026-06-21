package com.petrolal.ahun.ahunmembersservice.application.ports;

import com.petrolal.ahun.ahunmembersservice.domain.dto.TelegramResponseDto;

public interface TelegramPort {

    TelegramResponseDto sendMessage();

}
