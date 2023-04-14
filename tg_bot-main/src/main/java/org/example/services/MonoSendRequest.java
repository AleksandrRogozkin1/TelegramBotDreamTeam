package org.example.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.currency.Bank;
import org.example.currency.Currency;
import org.example.currency.dto.CurrencyMonoDto;
import org.example.currency.dto.CurrencyRateDto;
import org.jsoup.Jsoup;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MonoSendRequest implements CurrencyService {
    private static final String URL = "https://api.monobank.ua/bank/currency";

    @Override
    public List<CurrencyRateDto> getRate() {
        try {
            String currencyResponse = Jsoup.connect(URL)
                    .ignoreContentType(true)
                    .get()
                    .body()
                    .text()
                    .replace(":840", ":USD") // змінюємо код валюти на її назву
                    .replace(":978", ":EUR")
                    .replace(":980", ":UAH");

            Type type = new TypeToken<List<CurrencyMonoDto>>() {
            }.getType();
            List<CurrencyMonoDto> currencyMonoDtoList = new Gson().fromJson(currencyResponse, type);
            List<CurrencyRateDto> filteredListOfCurrency = new ArrayList<>();

            currencyMonoDtoList.stream()
                    .filter(curr -> Currency.UAH.equals(curr.getCurrencyCodeB()) &&
                            Currency.EUR.equals(curr.getCurrencyCodeA()) ||
                            Currency.USD.equals(curr.getCurrencyCodeA()))
                    .forEach(item -> {
                        CurrencyRateDto currencyRateDto = new CurrencyRateDto(Bank.MONOBANK, item.getCurrencyCodeA(),
                                item.getRateBuy(), item.getRateSell(), Calendar.getInstance());
                        filteredListOfCurrency.add(currencyRateDto);
                    });
            return filteredListOfCurrency;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
