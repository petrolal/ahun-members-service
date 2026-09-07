package com.petrolal.ahun.members.application.usecases;

import com.petrolal.ahun.members.application.ports.MemberPort;
import com.petrolal.ahun.members.application.ports.TelegramPort;
import com.petrolal.ahun.members.application.ports.TelegramSenderPort;
import com.petrolal.ahun.members.domain.dto.TelegramResponseDto;
import com.petrolal.ahun.members.domain.model.Member;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class TelegramUseCases implements TelegramPort {

  private final TelegramSenderPort telegramSenderPort;
  private final MemberPort memberPort;

  private static final DateTimeFormatter outFormat = DateTimeFormatter.ofPattern("d/M/yyyy");

  public TelegramUseCases(TelegramSenderPort telegramSenderPort, MemberPort memberPort) {
    this.telegramSenderPort = telegramSenderPort;
    this.memberPort = memberPort;
  }

  private String convertMemberCurrentMonthToTelegram(Boolean daily) {
    List<Member> members;

    if (daily) {
      members = memberPort.getBirthdaysByMonthAndDate();
    } else {
      members = memberPort.getMembersByCurrentMonth();
    }

    String currentMonth = getMonthName(LocalDate.now().getMonthValue());
    StringBuilder sb = new StringBuilder();

    String message =
        String.format("\uD83C\uDF89 Aniversáriantes de %s \n\n", daily ? "Hoje" : currentMonth);
    sb.append(message);

    if (members.isEmpty()) {
      sb.append("Nenhum aniversariante encontrado!\n");
      return sb.toString();
    }

    members.forEach(
        member -> {
          DateTimeFormatter formatter =
              new DateTimeFormatterBuilder()
                  .appendOptional(DateTimeFormatter.ISO_LOCAL_DATE)
                  .appendOptional(DateTimeFormatter.ofPattern("M/d/yyyy"))
                  .appendOptional(DateTimeFormatter.ofPattern("d/M/yyyy"))
                  .toFormatter();

          LocalDate data = LocalDate.parse(member.getBirthday().toString(), formatter);

          sb.append("• ")
              .append(member.getMemberName())
              .append(" - ")
              .append(data.format(outFormat))
              .append(" \n");
        });

    return sb.toString();
  }

  public String getMonthName(int valor) {
    if (valor < 1 || valor > 12) return "Invalid Month";
    return Month.of(valor).getDisplayName(TextStyle.FULL, Locale.of("pt", "BR"));
  }

  @Override
  public TelegramResponseDto sendMonthlyMessage() {
    return telegramSenderPort.sendNotification(convertMemberCurrentMonthToTelegram(false));
  }

  @Override
  public TelegramResponseDto sendDailyMessage() {
    return telegramSenderPort.sendNotification(convertMemberCurrentMonthToTelegram(true));
  }

  @Override
  public TelegramResponseDto sendMonthlyMessage(String chatId) {
    return telegramSenderPort.sendNotification(chatId, convertMemberCurrentMonthToTelegram(false));
  }

  @Override
  public TelegramResponseDto sendDailyMessage(String chatId) {
    return telegramSenderPort.sendNotification(chatId, convertMemberCurrentMonthToTelegram(true));
  }

  @Override
  public TelegramResponseDto sendMembersList(String chatId) {
    List<Member> members = memberPort.getMembers();
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("📋 Total de membros cadastrados: %d\n\n", members.size()));
    members.forEach(m -> sb.append("• ").append(m.getMemberName()).append("\n"));
    return telegramSenderPort.sendNotification(chatId, sb.toString());
  }
}
