package org.example.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.example.currency.Bank;
import org.example.currency.Currency;
import org.example.currency.dto.CurrencyPrivatDto;
import org.example.currency.dto.CurrencyRateDto;
import org.jsoup.Jsoup;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PrivatSendRequest implements CurrencyService {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String URL = "https://api.privatbank.ua/p24api/pubinfo?exchange&coursid=5";

    @Override
    public List<CurrencyRateDto> getRate(Currency currency) {
        try {
            String currencyResponse = Jsoup.connect(URL)
                    .ignoreContentType(true)
                    .get()
                    .body()
                    .text();

            Type type = new TypeToken<List<CurrencyPrivatDto>>() {
            }.getType();
            List<CurrencyPrivatDto> currencyPrivatDtoList = new Gson().fromJson(currencyResponse, type);

            List<CurrencyRateDto> filteredListOfCurrency = new ArrayList<>();

            currencyPrivatDtoList.stream()
                    .filter(curr -> Currency.UAH.equals(curr.getBase_ccy()) &&
                            currency.equals(curr.getCcy()))
                    .forEach(item -> {
                        CurrencyRateDto currencyRateDto = new CurrencyRateDto(Bank.PRIVATBANK, currency,
                                item.getBuy(), item.getSale(), Calendar.getInstance());
                        filteredListOfCurrency.add(currencyRateDto);

                    });
            return filteredListOfCurrency;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
