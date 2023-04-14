package org.example.telegram;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.currency.Bank;
import org.example.currency.Currency;
import org.example.telegram.menu.MenuCreationService;
import org.example.user.User;
import org.example.utils.FileUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDate;

public class CurrencyBot extends TelegramLongPollingBot {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String BOT_TOKEN = "5609499310:AAGcA0gZX6EeQKy07ALNq_z8_9XvacLJN2Y";
    private static final String BOT_USERNAME = "@CurrencyBotByDreamTeamBot";

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                long userId = update.getMessage().getChatId(); // отримуємо tg-id юзера
                String userFirstName = update.getMessage().getFrom().getFirstName(); // отримуємо юзернейм
                User user = new User(userId, userFirstName, LocalDate.now().toString()); // створюємо об'єкт юзера

                // Перевірка, якщо ще немає юзера з таким Telegram ID, то додаєм його у список
                if (FileUtils.getUserSettingsDtoList().stream().noneMatch(item -> item.getUserId() == userId)) {
                    FileUtils.getUserSettingsDtoList().add(user);

                    FileUtils.saveInfoToJsonFile(GSON.toJson(FileUtils.getUserSettingsDtoList()),
                            FileUtils.getUSER_SETTINGS_FILENAME());
                }
                SendMessage startMenuMessage = new MenuCreationService().getStartMenu(userId);
                execute(startMenuMessage);
            }

            if (update.hasCallbackQuery()) {
                long userId = update.getCallbackQuery().getMessage().getChatId();
                long messageId = update.getCallbackQuery().getMessage().getMessageId();
                String callData = update.getCallbackQuery().getData();
                SendMessage outMessage = new SendMessage();
                outMessage.setChatId(userId);


                switch (callData) {
                    case "GET_CURRENCY":
                        outMessage.setText(FileUtils.getRateByUserSettings(userId));
                        SendMessage startMenuMessage = new MenuCreationService().getStartMenu(userId);
                        execute(outMessage);
                        execute(startMenuMessage);
                        break;
                    case "GET_SETTINGS_BACK":
                        startMenuMessage = new MenuCreationService().getStartMenu(userId); // викликаємо головне меню
                        execute(startMenuMessage);
                        break;
                    case "GET_BANK_SETTINGS":
                        EditMessageText bankMenuMassage = new MenuCreationService().getBankMenu(userId, messageId);
                        execute(bankMenuMassage);
                        break;
                    case "SET_MONOBANK":
                        FileUtils.changeUserSettingsData(userId, user -> user.setCurrentBank(Bank.MONOBANK));
                        bankMenuMassage = new MenuCreationService().getBankMenu(userId, messageId);
                        execute(bankMenuMassage);
                        break;
                    case "SET_NBU":
                        FileUtils.changeUserSettingsData(userId, user -> user.setCurrentBank(Bank.NBU));
                        bankMenuMassage = new MenuCreationService().getBankMenu(userId, messageId);
                        execute(bankMenuMassage);
                        break;
                    case "SET_PRIVATBANK":
                        FileUtils.changeUserSettingsData(userId, user -> user.setCurrentBank(Bank.PRIVATBANK));
                        bankMenuMassage = new MenuCreationService().getBankMenu(userId, messageId);
                        execute(bankMenuMassage);
                        break;
                    case "GET_SETTINGS":
                    case "GET_BANK_BACK":
                        SendMessage settingsMenuMessage = new MenuCreationService().getSettingsMenu(userId);
                        execute(settingsMenuMessage);
                        break;
                    case "GET_CURRENCY_SETTINGS":
                        EditMessageText currencyMenuMassage = new MenuCreationService().getCurrencyMenu(userId, messageId);
                        execute(currencyMenuMassage);
                        break;
                    case "SET_USD":
                        FileUtils.changeUserSettingsData(userId, user -> user.setCurrentCurrency(Currency.USD));
                        currencyMenuMassage = new MenuCreationService().getCurrencyMenu(userId, messageId);
                        execute(currencyMenuMassage);
                        break;
                    case "SET_EUR":
                        FileUtils.changeUserSettingsData(userId, user -> user.setCurrentCurrency(Currency.EUR));
                        currencyMenuMassage = new MenuCreationService().getCurrencyMenu(userId, messageId);
                        execute(currencyMenuMassage);
                        break;
                    case "GET_CURRENCY_BACK":
                    case "GET_NOTIFICATION_BACK":
                        settingsMenuMessage = new MenuCreationService().getSettingsMenu(userId);
                        execute(settingsMenuMessage);
                        break;
                    case "GET_NOTIFICATION_SETTINGS":
                        EditMessageText notificationMenuMassage = new MenuCreationService().setNotificationTimeMenu(userId, messageId);
                        execute(notificationMenuMassage);
                        break;
                    case "GET_HOME":
                        settingsMenuMessage = new MenuCreationService().getStartMenu(userId);
                        execute(settingsMenuMessage);
                        break;
                    case "SWITCH_NOTIFICATION":
                        notificationMenuMassage = new MenuCreationService().setNotificationTimeMenu(userId, messageId);
                        execute(notificationMenuMassage);
                        break;
                    case "GET_DECIMAL_SETTINGS":
                        EditMessageText precisionMenuMassage = new MenuCreationService().getDecimalPlacesMenu(userId, messageId);
                        execute(precisionMenuMassage);
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


