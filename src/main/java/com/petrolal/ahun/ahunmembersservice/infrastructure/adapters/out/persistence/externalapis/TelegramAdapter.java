package com.petrolal.ahun.ahunmembersservice.infrastructure.adapters.out.persistence.externalapis;

import com.petrolal.ahun.ahunmembersservice.domain.dto.TelegramMessageRequestDto;
import com.petrolal.ahun.ahunmembersservice.domain.dto.TelegramResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TelegramAdapter {

    @Value("${telegram.bot-token}")
    private  String botToken;

    @Value("${telegram.chat-id}")
    private String chatId;

    private final RestTemplate restTemplate = new RestTemplate();

    public TelegramResponseDto sendMessage(TelegramAdapter telegramAdapter, String text) {

        TelegramMessageRequestDto telegramDto = new TelegramMessageRequestDto(
                telegramAdapter.chatId,
                text,
                "Markdown"
        );

        String url = String.format("https://api.telegram.org/bot%s/sendMessage",
                telegramAdapter.botToken);

        return telegramAdapter.restTemplate.postForObject(url, telegramDto, TelegramResponseDto.class);
    }

}
