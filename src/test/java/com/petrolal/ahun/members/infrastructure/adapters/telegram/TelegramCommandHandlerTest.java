package com.petrolal.ahun.members.infrastructure.adapters.telegram;

import static org.mockito.Mockito.*;

import com.petrolal.ahun.members.application.ports.GoogleSheetPort;
import com.petrolal.ahun.members.application.ports.TelegramPort;
import com.petrolal.ahun.members.application.ports.TelegramSenderPort;
import com.petrolal.ahun.members.domain.model.Member;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

class TelegramCommandHandlerTest {

  private TelegramPort telegramPort;
  private GoogleSheetPort googleSheetPort;
  private TelegramSenderPort telegramSenderPort;
  private TelegramCommandHandler commandHandler;

  @BeforeEach
  void setUp() {
    telegramPort = mock(TelegramPort.class);
    googleSheetPort = mock(GoogleSheetPort.class);
    telegramSenderPort = mock(TelegramSenderPort.class);
    commandHandler =
        new TelegramCommandHandler(
            telegramPort, googleSheetPort, telegramSenderPort, "http://localhost:8081");
  }

  private Update createUpdateWithText(String text) {
    Update update = new Update();
    Message message = new Message();
    message.setText(text);
    Chat chat = new Chat();
    chat.setId(12345L);
    message.setChat(chat);
    update.setMessage(message);
    return update;
  }

  @Test
  void shouldDispatchAniversariantesCommand() {
    Update update = createUpdateWithText("/aniversariantes");
    commandHandler.handle(update);
    verify(telegramPort).sendMonthlyMessage("12345");
  }

  @Test
  void shouldDispatchAniversariantesHojeCommand() {
    Update update = createUpdateWithText("/aniversariantes_hoje");
    commandHandler.handle(update);
    verify(telegramPort).sendDailyMessage("12345");
  }

  @Test
  void shouldDispatchMembrosCommand() {
    Update update = createUpdateWithText("/membros");
    commandHandler.handle(update);
    verify(telegramPort).sendMembersList("12345");
  }

  @Test
  void shouldDispatchSincronizarCommand() {
    when(googleSheetPort.syncSheet())
        .thenReturn(
            List.of(
                new Member(
                    null, "Test", "test@test.com", LocalDate.now(), LocalDateTime.now())));

    Update update = createUpdateWithText("/sincronizar");
    commandHandler.handle(update);

    verify(googleSheetPort).syncSheet();
    verify(telegramSenderPort, atLeastOnce()).sendNotification(eq("12345"), anyString());
  }

  @Test
  void shouldHandleGroupChatCommandWithBotMention() {
    Update update = createUpdateWithText("/aniversariantes@AhunMembersBot");
    commandHandler.handle(update);
    verify(telegramPort).sendMonthlyMessage("12345");
  }
}
