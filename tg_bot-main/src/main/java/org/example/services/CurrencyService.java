package org.example.services;

import org.example.currency.Currency;
import org.example.currency.dto.CurrencyRateDto;

import java.util.List;
/*
    Інтерфейс відправки GET-запроса на сайт банка.
 */
public interface CurrencyService {
    List<CurrencyRateDto> getRate();
}
