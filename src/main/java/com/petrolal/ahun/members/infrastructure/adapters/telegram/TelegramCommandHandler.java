package com.petrolal.ahun.members.infrastructure.adapters.telegram;

import com.petrolal.ahun.members.application.ports.GoogleSheetPort;
import com.petrolal.ahun.members.application.ports.TelegramPort;
import com.petrolal.ahun.members.application.ports.TelegramSenderPort;
import com.petrolal.ahun.members.domain.model.Member;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramCommandHandler {

  private static final Logger log = LoggerFactory.getLogger(TelegramCommandHandler.class);

  private final TelegramPort telegramPort;
  private final GoogleSheetPort googleSheetPort;
  private final TelegramSenderPort telegramSenderPort;
  private final String dutyServiceUrl;
  private final RestClient restClient;

  public TelegramCommandHandler(
      TelegramPort telegramPort,
      GoogleSheetPort googleSheetPort,
      TelegramSenderPort telegramSenderPort,
      @Value("${ahun.duty.url:https://ahun-duty-service-69314073740.us-central1.run.app}")
          String dutyServiceUrl) {
    this.telegramPort = telegramPort;
    this.googleSheetPort = googleSheetPort;
    this.telegramSenderPort = telegramSenderPort;
    this.dutyServiceUrl = dutyServiceUrl;
    this.restClient = RestClient.builder().baseUrl(dutyServiceUrl).build();
  }

  public void handle(Update update) {
    if (update == null || !update.hasMessage() || !update.getMessage().hasText()) {
      return;
    }

    String rawText = update.getMessage().getText().trim();
    String chatId = update.getMessage().getChatId().toString();
    String command = rawText.split("\\s+")[0].split("@")[0].toLowerCase();

    log.info("Received Telegram command: {} from chatId: {}", command, chatId);

    switch (command) {
      case "/aniversariantes" -> telegramPort.sendMonthlyMessage(chatId);
      case "/aniversariantes_hoje" -> telegramPort.sendDailyMessage(chatId);
      case "/membros" -> telegramPort.sendMembersList(chatId);
      case "/sincronizar" -> handleSync(chatId);
      case "/funcao", "/proxima_funcao", "/cartao_funcao" -> forwardToDutyService(update, chatId);
      default -> log.debug("Ignoring non-command or unhandled text: {}", rawText);
    }
  }

  private void handleSync(String chatId) {
    telegramSenderPort.sendNotification(chatId, "⏳ Sincronizando com a planilha do Google...");
    try {
      List<Member> synced = googleSheetPort.syncSheet();
      telegramSenderPort.sendNotification(
          chatId,
          String.format(
              "✅ Planilha sincronizada com sucesso! Total de %d membros atualizados.",
              synced.size()));
    } catch (Exception e) {
      log.error("Error syncing Google Sheet: {}", e.getMessage(), e);
      telegramSenderPort.sendNotification(
          chatId, "❌ Erro ao sincronizar a planilha: " + e.getMessage());
    }
  }

  private void forwardToDutyService(Update update, String chatId) {
    try {
      log.info("Forwarding update to duty service at: {}/api/telegram/webhook", dutyServiceUrl);
      restClient
          .post()
          .uri("/api/telegram/webhook")
          .body(update)
          .retrieve()
          .toBodilessEntity();
    } catch (Exception e) {
      log.error("Failed to forward command to duty service: {}", e.getMessage(), e);
      telegramSenderPort.sendNotification(
          chatId,
          "⚠️ O serviço de funções (ahun-duty-service) está temporariamente indisponível.");
    }
  }
}
