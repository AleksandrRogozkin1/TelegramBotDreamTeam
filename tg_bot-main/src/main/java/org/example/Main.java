package org.example;

import com.google.gson.GsonBuilder;
import org.example.currency.Currency;
import org.example.services.PrivatSendRequest;
import org.example.telegram.CurrencyBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new CurrencyBot());


            /*
                Тест сервісу MonoSendRequest,
                 метод приймає Currency (в залежності від того, яку валюту ми хочемо отримати)
            */
            // System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(new PrivatSendRequest().getRate(Currency.EUR)));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}