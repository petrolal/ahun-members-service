package com.petrolal.ahun.members.infrastructure.adapters.telegram;

import com.petrolal.ahun.members.application.ports.TelegramSenderPort;
import com.petrolal.ahun.members.domain.dto.TelegramResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBotAdapter extends TelegramLongPollingBot implements TelegramSenderPort {

  private final String botUsername;
  private final String chatId;

  public TelegramBotAdapter(
      @Value("${telegram.bot-token}") String botToken,
      @Value("${telegram.chat-id}") String chatId) {
    super(botToken);
    this.botUsername = "AhunMembersBot"; // Could also be parameterized
    this.chatId = chatId;
  }

  @Override
  public String getBotUsername() {
    return botUsername;
  }

  @Override
  public void onUpdateReceived(Update update) {
    // Not used right now
  }

  @Override
  public TelegramResponseDto sendNotification(String message) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(chatId);
    sendMessage.setText(message);

    try {
      execute(sendMessage);
      return new TelegramResponseDto(true, "Message sent successfully");
    } catch (TelegramApiException e) {
      e.printStackTrace();
      return new TelegramResponseDto(false, e.getMessage());
    }
  }
}
