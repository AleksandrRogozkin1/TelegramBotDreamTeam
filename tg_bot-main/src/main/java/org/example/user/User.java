package org.example.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.currency.Bank;
import org.example.currency.Currency;

@Getter
@Setter
@AllArgsConstructor // використав бібліотеку Lombok для автоматичного генерування геттерів/сеттерів/конструктора

/*
    Клас для збереження даних (налаштувань) користувача
*/
public class User {
    private long userId; // телеграм ід користувача
    private String username; // ім'я
    private Currency currentCurrency; // поточна валюта
    private Bank currentBank; // поточний банк
    private int decimalPlaces; // скільки знаків після коми
    private final String registrationDate; // дата реєстрації

}
