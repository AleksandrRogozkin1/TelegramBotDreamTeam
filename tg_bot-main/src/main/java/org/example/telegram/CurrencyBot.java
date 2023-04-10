package org.example.telegram;

import org.example.currency.Bank;
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
                SendMessage startMenuMessage = new MenuCreationService().getStartMenu(userId); // викликаємо головне меню
                execute(startMenuMessage);

                User user = new User(userId, userFirstName, Currency.USD, Bank.MONOBANK, 2,
                        LocalDate.now().toString()); // створюємо об'єкт юзера
                FileUtils.saveUserToJsonFile(user); // запис його у файл
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
                }

                else if (callData.equals("GET_SETTINGS")) {
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


