package org.example.services;

import org.example.currency.Currency;
import org.example.currency.dto.CurrencyRateDto;

import java.util.List;
/*
    Інтерфейс відправки GET-запроса на сайт банка.
 */
public interface CurrencyService {
    // Метод приймає Currency, але цей функціонал з вибором банка ще не реалізований
    List<CurrencyRateDto> getRate(Currency currency);
}
