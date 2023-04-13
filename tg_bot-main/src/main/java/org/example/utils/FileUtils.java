package org.example.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import lombok.Getter;
import org.example.currency.Bank;
import org.example.currency.dto.CurrencyRateDto;
import org.example.user.User;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileUtils {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    /*
        CURRENCY_RATES_FILENAME - шлях до файлу, де будуть зберігатись курси валют
        USER_SETTINGS_FILENAME - шлях до файлу, де будуть зберігатись налаштування юзера
     */
    @Getter
    private static final String CURRENCY_RATES_FILENAME = "tg_bot-main/src/main/resources/currencyRates.json";
    @Getter
    private static final String USER_SETTINGS_FILENAME = "tg_bot-main/src/main/resources/users.json";

    /*
        Списки з об'єктами (налаштування юзера та курси валют відповідно).
     */
    @Getter
    private static final List<User> userSettingsDtoList =
            getListObjectFromJson(USER_SETTINGS_FILENAME, User.class);
    @Getter
    private static final List<CurrencyRateDto> currencyRateDtoList =
            getListObjectFromJson(CURRENCY_RATES_FILENAME, CurrencyRateDto.class);

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
        Метод для фільтрації Json-ів по банку зі списку.
        (Поки що не потрібен і не використовується)
     */
    public static List<CurrencyRateDto> getFilteredListOfCurrencies(Bank bank) {
        return currencyRateDtoList.stream()
                .filter(currency -> bank.equals(currency.getBank()))
                .collect(Collectors.toList());
    }

    public static void changeUserCurrentBankData(long userId, Bank bank) {
        userSettingsDtoList.stream()
                .filter(user -> user.getUserId() == userId)
                .forEach(user -> user.setCurrentBank(bank));

        saveInfoToJsonFile(GSON.toJson(userSettingsDtoList), USER_SETTINGS_FILENAME);
    }

    /*
        Метод який повертає список об'єктів з файлу Json (десереалізація)
        Зробив через дженерики, щоб не писати кілька методів
     */
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
}
