package com.petrolal.ahun.ahunmembersservice.infrastructure.adapters;

import com.petrolal.ahun.ahunmembersservice.application.ports.TelegramPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MonthlyNotificationServiceTest {

    @Mock
    private TelegramPort telegramPort;

    private MonthlyNotificationService notificationService;

    @BeforeEach
    void setUp() {
        notificationService = new MonthlyNotificationService(telegramPort);
    }

    @Test
    void shouldTriggerMonthlyNotification() {
        notificationService.sendMonthlyMembersNotification();
        verify(telegramPort).sendMonthlyMessage();
    }

    @Test
    void shouldTriggerDailyNotification() {
        notificationService.checkBirthdaysEveryDay();
        verify(telegramPort).sendDailyMessage();
    }
}
