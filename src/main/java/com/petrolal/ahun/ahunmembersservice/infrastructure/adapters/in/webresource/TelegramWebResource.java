package com.petrolal.ahun.ahunmembersservice.infrastructure.adapters.in.webresource;

import com.petrolal.ahun.ahunmembersservice.application.ports.TelegramPort;
import com.petrolal.ahun.ahunmembersservice.domain.dto.TelegramResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
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
    TelegramResponseDto sendMessage() {
        return telegramPort.sendMessage();
    }

}
