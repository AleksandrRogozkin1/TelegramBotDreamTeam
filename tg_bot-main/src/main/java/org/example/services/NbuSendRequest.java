package org.example.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.currency.Bank;
import org.example.currency.Currency;
import org.example.currency.dto.CurrencyNbuDto;
import org.example.currency.dto.CurrencyRateDto;
import org.jsoup.Jsoup;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NbuSendRequest implements CurrencyService {
    private static final String URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";

    @Override
    public List<CurrencyRateDto> getRate() {
        try {
            String currencyResponse = Jsoup.connect(URL)
                    .ignoreContentType(true)
                    .get()
                    .body()
                    .text();

            Type type = new TypeToken<List<CurrencyNbuDto>>() {
            }.getType();
            List<CurrencyNbuDto> currencyNbuDtoList = new Gson().fromJson(currencyResponse, type);
            List<CurrencyRateDto> filteredListOfCurrency = new ArrayList<>();

            currencyNbuDtoList.stream()
                    .filter(curr -> Currency.USD.equals(curr.getCc()) ||
                            Currency.EUR.equals(curr.getCc()))
                    .forEach(item -> {
                        CurrencyRateDto currencyRateDto = new CurrencyRateDto(Bank.NBU, item.getCc(),
                                item.getRate(), item.getRate(), Calendar.getInstance());
                        filteredListOfCurrency.add(currencyRateDto);
                    });
            return filteredListOfCurrency;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}