package com.petrolal.ahun.ahunmembersservice.infrastructure.adapters;

import com.petrolal.ahun.ahunmembersservice.application.ports.TelegramPort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MonthlyNotificationService {

    private final TelegramPort telegramPort;

    public MonthlyNotificationService(TelegramPort telegramPort) {
        this.telegramPort = telegramPort;
    }

    @Scheduled(cron = "0 0 9 1 * ?", zone = "America/Sao_Paulo")
    public void sendMonthlyMembersNotification() {
        telegramPort.sendMonthlyMessage();
    }

    @Scheduled(cron = "0 0 8 * * *", zone = "America/Sao_Paulo")
    public void checkBirthdaysEveryDay() {
        telegramPort.sendDailyMessage();
    }
}