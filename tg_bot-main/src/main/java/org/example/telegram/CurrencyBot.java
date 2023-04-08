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
    private static final String BOT_TOKEN = "5904506234:AAFiUWaZE0NsSJgIiVWEQ7BMZxfe6MTrOKI";
    private static final String BOT_USERNAME = "@TestBotJavaGOIT_bot";

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
                // Отримуємо певний ключ нажатої кнопки (у кожної він свій, змінити можна в класі KeyboardCreationService
                String callData = update.getCallbackQuery().getData();
                // отримуємо id користувача
                long userId = update.getCallbackQuery().getMessage().getChatId();
                SendMessage outMessage = new SendMessage();
                outMessage.setChatId(userId);

                /*
                    Якщо нажата кнопка з ключом "GET_CURRENCY", то надсилається get-запрос на сайт ПриватБанка
                    і відпавляється користувачу
                */
                if (callData.equals("GET_CURRENCY")) {
                    outMessage.setText(new PrivatSendRequest().getRate(Currency.USD).toString());
                    SendMessage startMenuMessage = new MenuCreationService().getStartMenu(userId);
                    execute(outMessage);
                    execute(startMenuMessage);
                }
                /*
                    Налаштування користувача ще не реалізовані
                */
                else if (callData.equals("GET_SETTINGS")) {
                    outMessage.setText("1");
                    execute(outMessage);
                    SendMessage startMenuMessage = new MenuCreationService().getStartMenu(userId);
                    execute(startMenuMessage);
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


