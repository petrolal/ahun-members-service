package com.petrolal.ahun.ahunmembersservice.application.usecases;

import com.petrolal.ahun.ahunmembersservice.application.ports.MemberPort;
import com.petrolal.ahun.ahunmembersservice.application.ports.TelegramPort;
import com.petrolal.ahun.ahunmembersservice.application.ports.TelegramSenderPort;
import com.petrolal.ahun.ahunmembersservice.domain.dto.TelegramResponseDto;
import com.petrolal.ahun.ahunmembersservice.domain.model.Member;

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

    private final static DateTimeFormatter outFormat = DateTimeFormatter.ofPattern("d/M/yyyy");

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

        String message = String.format("\uD83C\uDF89 Aniversáriantes de %s \n\n", daily ? "Hoje" : currentMonth);
        sb.append(message);

        members.forEach(member -> {
            DateTimeFormatter formatter = new DateTimeFormatterBuilder()
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
        return Month.of(valor).getDisplayName(TextStyle.FULL, new Locale("pt", "BR"));
    }

    @Override
    public TelegramResponseDto sendMonthlyMessage() {
        return telegramSenderPort.sendNotification(convertMemberCurrentMonthToTelegram(false));
    }

    @Override
    public TelegramResponseDto sendDailyMessage() {
        return telegramSenderPort.sendNotification(convertMemberCurrentMonthToTelegram(true));
    }
}
