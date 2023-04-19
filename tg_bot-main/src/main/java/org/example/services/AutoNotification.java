package org.example.services;

import org.example.telegram.CurrencyBot;
import org.example.telegram.CurrencyRateMessageBuilder;
import org.example.telegram.menu.MenuCreationService;
import org.example.user.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.Duration;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import static org.example.telegram.CurrencyRateMessageBuilder.getUserSettingsDtoList;

public class AutoNotification extends Thread{
    @Override
    public void run() {
        while (true) {
            LocalTime localTime = LocalTime.now(ZoneId.of("Europe/Kiev"));
            LocalTime nextHour = localTime.plusHours(1).truncatedTo(ChronoUnit.HOURS);
            Duration timeToNextHour = Duration.between(localTime, nextHour);
            if (timeToNextHour.isNegative()) {
                timeToNextHour = Duration.ofHours(24).plus(timeToNextHour);
            }
            if(localTime.getMinute() == 0 && localTime.getHour() >= 9 && localTime.getHour() <= 18) {
                sendNotification(localTime.getHour());
            }
            try {
                Thread.sleep(timeToNextHour.toMillis());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void sendNotification (int currentHour) {
        for (User userSetting: getUserSettingsDtoList()) {
            if (userSetting.getNotificationTime().equals(String.valueOf(currentHour))) {
                CurrencyBot currencyBot = new CurrencyBot();
                MenuCreationService menuCreationService = new MenuCreationService();
                SendMessage newMassage = menuCreationService.getAutoRateMenu(userSetting.getUserId());
                try {
                    currencyBot.execute(newMassage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
