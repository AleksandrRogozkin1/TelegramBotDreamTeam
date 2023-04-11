package org.example.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.example.currency.Bank;
import org.example.currency.Currency;
import org.example.currency.dto.CurrencyMonoDto;
import org.example.currency.dto.CurrencyRateDto;
import org.example.utils.FileUtils;
import org.jsoup.Jsoup;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class MonoSendRequest implements CurrencyService {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
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

            Type type = new TypeToken<List<CurrencyMonoDto>>() {
            }.getType();
            List<CurrencyMonoDto> currencyMonoDtoList = new Gson().fromJson(currencyResponse, type);

            // Список з відфільтрованимм валютами
            List<CurrencyRateDto> filteredListOfCurrency = new ArrayList<>();
            /*
                filter - проводить фільтрацію Json-ів згідно валюти, яку приймає метод в якості аргумента (currency).
                foreach - ітерація по відфільтрованих об'єктах та створення об'єктів класу CurrencyRateDto + запис їх у Json
             */
            currencyMonoDtoList.stream()
                    .filter(curr -> Currency.UAH.equals(curr.getCurrencyCodeB()) &&
                            currency.equals(curr.getCurrencyCodeA()))
                    .forEach(item -> {
                        // Створюємо об'єкт класу CurrencyRateDto з даними про валюту, отримані з відфільтрованих об'єктів
                        CurrencyRateDto currencyRateDto = new CurrencyRateDto(Bank.MONOBANK, currency,
                                item.getRateBuy(), item.getRateSell(), Calendar.getInstance());
                        filteredListOfCurrency.add(currencyRateDto); // Додаємо об'єкт до списку filteredListOfCurrency
                        FileUtils.saveInfoToJsonFile(GSON.toJson(Collections.singletonList(currencyRateDto)),
                                FileUtils.getCURRENCY_RATES_FILENAME()); // Записуємо створений об'єкт у Json
                    });
            /*
                Повернення списку об'єктів класу CurrencyRateDto з заданою валютою (список для того,
                    щоб при потребі додати мультиселект на вибір і повертати кілька валют, а не одну)
             */
            return filteredListOfCurrency;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
