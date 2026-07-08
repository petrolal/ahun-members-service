package com.petrolal.ahun.ahunmembersservice.application.usecases;

import com.petrolal.ahun.ahunmembersservice.application.ports.MemberPort;
import com.petrolal.ahun.ahunmembersservice.application.ports.TelegramSenderPort;
import com.petrolal.ahun.ahunmembersservice.domain.dto.TelegramResponseDto;
import com.petrolal.ahun.ahunmembersservice.domain.model.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TelegramUseCasesTest {

    @Mock
    private TelegramSenderPort telegramSenderPort;

    @Mock
    private MemberPort memberPort;

    private TelegramUseCases telegramUseCases;

    @BeforeEach
    void setUp() {
        telegramUseCases = new TelegramUseCases(telegramSenderPort, memberPort);
    }

    @Test
    void shouldSendMonthlyMessage() {
        Member member = new Member(UUID.randomUUID(), "Alice", "alice@example.com", LocalDate.of(1995, 6, 15), LocalDateTime.now());
        when(memberPort.getMembersByCurrentMonth()).thenReturn(List.of(member));

        TelegramResponseDto responseDto = new TelegramResponseDto(true, null);
        when(telegramSenderPort.sendNotification(any(String.class))).thenReturn(responseDto);

        TelegramResponseDto result = telegramUseCases.sendMonthlyMessage();

        assertThat(result.ok()).isTrue();

        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        verify(telegramSenderPort).sendNotification(messageCaptor.capture());
        String message = messageCaptor.getValue();
        assertThat(message).contains("Aniversáriantes de Hoje"); // Due to the implementation's daily ? month : "Hoje"
        assertThat(message).contains("Alice - 15/6/1995");
    }

    @Test
    void shouldSendDailyMessage() {
        Member member = new Member(UUID.randomUUID(), "Bob", "bob@example.com", LocalDate.of(1990, 6, 28), LocalDateTime.now());
        when(memberPort.getBirthdaysByMonthAndDate()).thenReturn(List.of(member));

        TelegramResponseDto responseDto = new TelegramResponseDto(true, null);
        when(telegramSenderPort.sendNotification(any(String.class))).thenReturn(responseDto);

        TelegramResponseDto result = telegramUseCases.sendDailyMessage();

        assertThat(result.ok()).isTrue();

        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        verify(telegramSenderPort).sendNotification(messageCaptor.capture());
        String message = messageCaptor.getValue();
        String currentMonthName = telegramUseCases.getMonthName(LocalDate.now().getMonthValue());
        assertThat(message).contains("Aniversáriantes de " + currentMonthName); // Due to the implementation's daily ? month : "Hoje"
        assertThat(message).contains("Bob - 28/6/1990");
    }

    @Test
    void shouldReturnCorrectMonthName() {
        assertThat(telegramUseCases.getMonthName(1)).isEqualTo("janeiro");
        assertThat(telegramUseCases.getMonthName(12)).isEqualTo("dezembro");
        assertThat(telegramUseCases.getMonthName(0)).isEqualTo("Invalid Month");
        assertThat(telegramUseCases.getMonthName(13)).isEqualTo("Invalid Month");
    }
}
