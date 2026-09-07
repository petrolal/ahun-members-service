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
  private final TelegramCommandHandler commandHandler;

  public TelegramBotAdapter(
      @Value("${telegram.bot-token}") String botToken,
      @Value("${telegram.chat-id}") String chatId,
      @org.springframework.context.annotation.Lazy TelegramCommandHandler commandHandler) {
    super(botToken);
    this.botUsername = "AhunMembersBot"; // Could also be parameterized
    this.chatId = chatId;
    this.commandHandler = commandHandler;
  }

  @Override
  public String getBotUsername() {
    return botUsername;
  }

  @Override
  public void onUpdateReceived(Update update) {
    commandHandler.handle(update);
  }

  @Override
  public TelegramResponseDto sendNotification(String message) {
    return sendNotification(this.chatId, message);
  }

  @Override
  public TelegramResponseDto sendNotification(String targetChatId, String message) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(targetChatId);
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
