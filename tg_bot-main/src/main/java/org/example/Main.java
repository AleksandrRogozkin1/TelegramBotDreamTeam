package org.example;

import org.example.services.AutoNotification;
import org.example.telegram.CurrencyBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new CurrencyBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        AutoNotification autoNotification = new AutoNotification();
        autoNotification.start();
    }
}