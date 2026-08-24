package com.petrolal.ahun.members.infrastructure.adapters.rest;

import com.petrolal.ahun.members.application.ports.TelegramPort;
import com.petrolal.ahun.members.domain.dto.SendMessageDto;
import com.petrolal.ahun.members.domain.dto.TelegramResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Telegram Messaging")
@RestController
@RequestMapping("/api/messaging")
public class TelegramWebResource {

    private final TelegramPort telegramPort;

    public TelegramWebResource(TelegramPort telegramPort) {
        this.telegramPort = telegramPort;
    }

    @PostMapping("send")
    TelegramResponseDto sendMessage(@RequestBody SendMessageDto sendMessageDto) {
        if (sendMessageDto.daily()) {
            return telegramPort.sendDailyMessage();
        }

        return telegramPort.sendMonthlyMessage();
    }

}
