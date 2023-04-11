package org.example.telegram;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.currency.Currency;
import org.example.services.PrivatSendRequest;
import org.example.telegram.menu.MenuCreationService;
import org.example.user.User;
import org.example.utils.FileUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDate;

public class CurrencyBot extends TelegramLongPollingBot {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    // Токен та юзернейм телеграм бота
    private static final String BOT_TOKEN = "5609499310:AAGcA0gZX6EeQKy07ALNq_z8_9XvacLJN2Y";
    private static final String BOT_USERNAME = "@CurrencyBotByDreamTeamBot";

    /*
        Обробник дій
    */
    @Override
    public void onUpdateReceived(Update update) {
        try {
            /*
                Обробник відправленого користувачем тексту
            */
            if (update.hasMessage() && update.getMessage().hasText()) {
                long userId = update.getMessage().getChatId(); // отримуємо tg-id юзера
                String userFirstName = update.getMessage().getFrom().getFirstName(); // отримуємо юзернейм
                User user = new User(userId, userFirstName, LocalDate.now().toString()); // створюємо об'єкт юзера

                // Перевірка, якщо ще немає юзера з таким Telegram ID, то додаєм його у список
                if (FileUtils.getUserSettingsDtoList().stream().noneMatch(item -> item.getUserId() == userId)) {
                    FileUtils.getUserSettingsDtoList().add(user);

                    // Витягуємо список юзерів з FileUtils та записуєм його у Json
                    FileUtils.saveInfoToJsonFile(GSON.toJson(FileUtils.getUserSettingsDtoList()),
                            FileUtils.getUSER_SETTINGS_FILENAME());
                }

                SendMessage startMenuMessage = new MenuCreationService().getStartMenu(userId); // викликаємо головне меню
                execute(startMenuMessage);
            }

            /*
                Обробник нажаття на кнопки (Отримати курс валют, налаштування)
            */
            if (update.hasCallbackQuery()) {
                // Обробник нажаття на кнопки
                long userId = update.getCallbackQuery().getMessage().getChatId();
                String callData = update.getCallbackQuery().getData();
                SendMessage outMessage = new SendMessage();
                outMessage.setChatId(userId);

                if (callData.equals("GET_CURRENCY")) {
                    // Надсилаємо get-запрос на сайт ПриватБанка і відправляємо відповідь користувачу
                    outMessage.setText(new PrivatSendRequest().getRate(Currency.USD).toString());
                    SendMessage startMenuMessage = new MenuCreationService().getStartMenu(userId);
                    execute(outMessage);
                    execute(startMenuMessage);
                } else if (callData.equals("GET_SETTINGS")) {
                    // Відображаємо меню налаштувань
                    SendMessage settingsMenuMessage = new MenuCreationService().getSettingsMenu(userId);
                    execute(settingsMenuMessage);
                    // ДЛЯ ПОДАЛЬШОЇ РЕАЛІЗАЦІЇ
                }
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}


