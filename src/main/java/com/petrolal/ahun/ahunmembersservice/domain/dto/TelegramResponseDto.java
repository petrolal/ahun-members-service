package com.petrolal.ahun.ahunmembersservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TelegramResponseDto(
        boolean ok,
        TelegramMessageDTO result
) {}

@JsonIgnoreProperties(ignoreUnknown = true)
record TelegramMessageDTO(
        @JsonProperty("message_id") Long messageId,
        TelegramUserDTO from,
        TelegramChatDTO chat,
        String text
) {}

@JsonIgnoreProperties(ignoreUnknown = true)
record TelegramUserDTO(
        Long id,
        @JsonProperty("is_bot") boolean isBot,
        @JsonProperty("first_name") String firstName,
        String username
) {}

@JsonIgnoreProperties(ignoreUnknown = true)
record TelegramChatDTO(
        Long id,
        @JsonProperty("first_name") String firstName,
        String type
) {}