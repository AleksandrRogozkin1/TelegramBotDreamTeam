package org.example.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import lombok.Getter;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    @Getter
    private static final String CURRENCY_RATES_FILENAME = "tg_bot-main/src/main/resources/currencyRates.json";
    @Getter
    private static final String USER_SETTINGS_FILENAME = "tg_bot-main/src/main/resources/users.json";

    public static <T> void saveInfoToJsonFile(T dataToSave, String fileName) {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(dataToSave.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> List<T> getListObjectFromJson(String fileName, Class<T> typeListElementClass) {
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
