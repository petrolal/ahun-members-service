package com.petrolal.ahun.ahunmembersservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TelegramMessageRequestDto(
        @JsonProperty("chat_id") String chat_id,
        @JsonProperty("text") String text,
        @JsonProperty("parse_mode") String parse_mode
) {
}
