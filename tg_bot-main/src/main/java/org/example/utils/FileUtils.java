package org.example.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import lombok.Getter;
import org.example.currency.Bank;
import org.example.currency.Currency;
import org.example.currency.dto.CurrencyRateDto;
import org.example.services.MonoSendRequest;
import org.example.services.NbuSendRequest;
import org.example.services.PrivatSendRequest;
import org.example.user.User;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FileUtils {
    @Getter
    private static final String CURRENCY_RATES_FILENAME = "tg_bot-main/src/main/resources/currencyRates.json";
    @Getter
    private static final String USER_SETTINGS_FILENAME = "tg_bot-main/src/main/resources/users.json";
    @Getter
    private static final List<User> userSettingsDtoList =
            getListObjectFromJson(USER_SETTINGS_FILENAME, User.class);
    @Getter
    private static final List<CurrencyRateDto> currencyRateDtoList =
            getListObjectFromJson(CURRENCY_RATES_FILENAME, CurrencyRateDto.class);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /*
    Метод, який створює Json файл та записує туди певні абстрактні дані (курс валют або юзерів)
    Зробив через дженерики, щоб не писати кілька методів
    */
    public static <T> void saveInfoToJsonFile(T dataToSave, String fileName) {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(dataToSave.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    Метод, який міняє якісь дані користувача(поточний банк, валюту).
    Другим аргументом може передаватись, наприклад, user -> user.setCurrentCurrency(Currency.USD); (зміна банку)
            або user -> user.setCurrentBank(Bank.NBU);
    */
    public static void changeUserSettingsData(long userId, Consumer<User> settingsUpdater) {
        userSettingsDtoList.stream()
                .filter(user -> user.getUserId() == userId)
                .forEach(settingsUpdater);
        saveInfoToJsonFile(GSON.toJson(userSettingsDtoList), USER_SETTINGS_FILENAME);
    }

    private static <T> List<T> getListObjectFromJson(String fileName, Class<T> typeListElementClass) {
        List<T> listOfObjects = new ArrayList<>();
        try (JsonReader reader = new JsonReader(new FileReader(fileName))) {
            Type type = TypeToken.getParameterized(List.class, typeListElementClass).getType();
            listOfObjects = new Gson().fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (listOfObjects == null) listOfObjects = new ArrayList<>();
        return listOfObjects;
    }

    public static String getRateByUserSettings(long userId) {
        User user = getUserById(userId);

        Bank bank = user.getCurrentBank();
        Currency currency = user.getCurrentCurrency();

        CurrencyRateDto currencyRate = getCurrencyRateByBankAndCurrency(bank, currency);

        if (currencyRate == null || isCurrencyRateOutdated(currencyRate)) {
            updateCurrencyRates();
            currencyRate = getCurrencyRateByBankAndCurrency(bank, currency);
        }
        return buildCurrencyRateString(user, currencyRate);
    }

    private static User getUserById(long userId) {
        return userSettingsDtoList.stream()
                .filter(settings -> settings.getUserId() == userId)
                .findFirst()
                .orElse(null);
    }

    private static CurrencyRateDto getCurrencyRateByBankAndCurrency(Bank bank, Currency currency) {
        return currencyRateDtoList.stream()
                .filter(currencyRateDto -> currencyRateDto.getBank().equals(bank) &&
                        currencyRateDto.getCurrency().equals(currency))
                .findFirst()
                .orElse(null);
    }

    private static boolean isCurrencyRateOutdated(CurrencyRateDto currencyRate) {
        return System.currentTimeMillis() - currencyRate.getRateDate().getTimeInMillis() > 300000;
    }

    private static void updateCurrencyRates() {
        currencyRateDtoList.clear();
        currencyRateDtoList.addAll(new PrivatSendRequest().getRate());
        currencyRateDtoList.addAll(new NbuSendRequest().getRate());
        currencyRateDtoList.addAll(new MonoSendRequest().getRate());
        FileUtils.saveInfoToJsonFile(GSON.toJson(currencyRateDtoList), CURRENCY_RATES_FILENAME);
    }

    private static String buildCurrencyRateString(User user, CurrencyRateDto currencyRate) {
        int decimalPlaces = user.getDecimalPlaces();
        String buyFormat = "%." + decimalPlaces + "f";
        String sellFormat = "%." + decimalPlaces + "f";

        return "Currency from " + currencyRate.getBank() + "\n" +
                "-----------------------------------------\n" +
                currencyRate.getCurrency() + "/UAH:\n" +
                "Buy: " + String.format(buyFormat, currencyRate.getBuy()) + "\n" +
                "Sell: " + String.format(sellFormat, currencyRate.getSell()) + "\n" +
                "-----------------------------------------\n" +
                "Last upd in: " + currencyRate.getFormattedRateDate();
    }
}
