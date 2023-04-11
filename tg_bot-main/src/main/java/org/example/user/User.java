package org.example.user;

import lombok.Getter;
import lombok.Setter;
import org.example.currency.Bank;
import org.example.currency.Currency;

@Getter
@Setter
/*
    Клас для збереження даних (налаштувань) користувача
*/
public class User {
    private final String registrationDate; // дата реєстрації
    private long userId; // телеграм ід користувача
    private String username; // ім'я
    private Currency currentCurrency; // поточна валюта
    private Bank currentBank; // поточний банк
    private int decimalPlaces; // скільки знаків після коми

    /*
        Конструктор (для того, щоб при реєстрації нового юзера, він мав вже якісь базові налаштування)
     */
    public User(long userId, String username, String registrationDate) {
        this.userId = userId;
        this.username = username;
        this.registrationDate = registrationDate;
        // Параметри по замовчуванню
        currentCurrency = Currency.USD;
        currentBank = Bank.PRIVATBANK;
        decimalPlaces = 2;
    }
}
