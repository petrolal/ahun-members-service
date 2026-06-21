package com.petrolal.ahun.ahunmembersservice.infrastructure.adapters.out.persistence;

import com.petrolal.ahun.ahunmembersservice.application.ports.TelegramPort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MonthlyNotificationService {

    private final TelegramPort telegramPort;

    public MonthlyNotificationService(TelegramPort telegramPort) {
        this.telegramPort = telegramPort;
    }

    @Scheduled(cron = "0 * * * * ?")
//    @Scheduled(cron = "0 0 9 1 * ?", zone = "America/Sao_Paulo")
    public void sendMonthlyMembersNotification() {
        telegramPort.sendMessage();
    }
}