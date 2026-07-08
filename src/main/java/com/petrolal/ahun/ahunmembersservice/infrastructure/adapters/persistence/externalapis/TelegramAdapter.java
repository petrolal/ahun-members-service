package com.petrolal.ahun.ahunmembersservice.infrastructure.adapters.persistence.externalapis;

import com.petrolal.ahun.ahunmembersservice.application.ports.TelegramSenderPort;
import com.petrolal.ahun.ahunmembersservice.domain.dto.TelegramMessageRequestDto;
import com.petrolal.ahun.ahunmembersservice.domain.dto.TelegramResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TelegramAdapter implements TelegramSenderPort {

    @Value("${telegram.bot-token}")
    private String botToken;

    @Value("${telegram.chat-id}")
    private String chatId;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public TelegramResponseDto sendNotification(String text) {
        TelegramMessageRequestDto telegramDto = new TelegramMessageRequestDto(
                chatId,
                text,
                "Markdown"
        );

        String url = String.format("https://api.telegram.org/bot%s/sendMessage", botToken);

        return restTemplate.postForObject(url, telegramDto, TelegramResponseDto.class);
    }
}
