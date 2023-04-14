package org.example.user;

import lombok.Getter;
import lombok.Setter;
import org.example.currency.Bank;
import org.example.currency.Currency;

import javax.management.Notification;

@Getter
@Setter
public class User {
    private final String registrationDate;
    private long userId;
    private String username;
    private Currency currentCurrency;
    private Bank currentBank;
    private int decimalPlaces;
    private String notificationTime;


    public User(long userId, String username, String registrationDate) {
        this.userId = userId;
        this.username = username;
        this.registrationDate = registrationDate;
        // Параметри по замовчуванню
        currentCurrency = Currency.USD;
        currentBank = Bank.PRIVATBANK;
        decimalPlaces = 2;
        notificationTime = "OFF";
    }

}
