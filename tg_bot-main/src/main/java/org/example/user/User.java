package org.example.user;

import lombok.Getter;
import lombok.Setter;
import org.example.currency.Bank;
import org.example.currency.Currency;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class User {
    private final String registrationDate;
    private long userId;
    private String username;
    private String notificationTime;
    private List<Currency> currentCurrency;
    private Bank currentBank;
    private int decimalPlaces;

    public User(long userId, String username, String registrationDate) {
        this.userId = userId;
        this.username = username;
        this.registrationDate = registrationDate;
        currentCurrency = new ArrayList<>(List.of(Currency.USD));
        currentBank = Bank.PRIVATBANK;
        decimalPlaces = 2;
        notificationTime = "OFF";
    }
}
