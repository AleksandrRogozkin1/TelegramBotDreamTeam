package org.example.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.currency.Currency;
import org.example.currency.dto.CurrencyMonoDto;
import org.example.currency.dto.CurrencyRateDto;
import org.jsoup.Jsoup;

import java.lang.reflect.Type;
import java.util.List;

public class MonoSendRequest implements CurrencyService {
    // url по якому будем відправляти get запрос
    private static final String URL = "https://api.monobank.ua/bank/currency";

    @Override
    public List<CurrencyRateDto> getRate(Currency currency) {
        try {
            String currencyResponse = Jsoup.connect(URL)
                    .ignoreContentType(true)
                    .get()
                    .body()
                    .text()
                    .replace(":840", ":USD") // змінюємо код валюти на її назву
                    .replace(":978", ":EUR")
                    .replace(":980", ":UAH");

            Type type = new TypeToken<List<CurrencyMonoDto>>() {}.getType();
            return new Gson().fromJson(currencyResponse, type); // Парсинг Json в об'єкти
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
