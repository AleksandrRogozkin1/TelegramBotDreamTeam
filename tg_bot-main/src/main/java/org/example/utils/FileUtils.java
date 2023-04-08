package org.example.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.user.User;

import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {
    /*
    Метод, який створює Json файл та записує туди дані користувача
    */
    public static void saveUserToJsonFile(User user) {
        String fileName = String.format("id-%d-username-%s.json", user.getUserId(), user.getUsername());

        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            Gson gson = new GsonBuilder()
                    .serializeNulls()
                    .setPrettyPrinting()
                    .create();
            fos.write(gson.toJson(user).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
