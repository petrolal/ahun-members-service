package com.petrolal.ahun.members.infrastructure.adapters.rest;

import com.petrolal.ahun.members.infrastructure.adapters.telegram.TelegramCommandHandler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;

@Tag(name = "Telegram Webhook")
@RestController
@RequestMapping("/api/telegram")
public class TelegramWebhookResource {

  private final TelegramCommandHandler commandHandler;

  public TelegramWebhookResource(TelegramCommandHandler commandHandler) {
    this.commandHandler = commandHandler;
  }

  @PostMapping("/webhook")
  public ResponseEntity<Void> onWebhookUpdate(@RequestBody Update update) {
    commandHandler.handle(update);
    return ResponseEntity.ok().build();
  }
}
