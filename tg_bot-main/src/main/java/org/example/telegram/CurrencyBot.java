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
import java.util.ResourceBundle;

public class CurrencyBot extends TelegramLongPollingBot {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("bot");

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                long userId = update.getMessage().getChatId();
                String userFirstName = update.getMessage().getFrom().getFirstName();
                User user = new User(userId, userFirstName, LocalDate.now().toString());

                if (CurrencyRateMessageBuilder.getUserSettingsDtoList().stream().noneMatch(item -> item.getUserId() == userId)) {
                    CurrencyRateMessageBuilder.getUserSettingsDtoList().add(user);
                    FileUtils.saveInfoToJsonFile(GSON.toJson(CurrencyRateMessageBuilder.getUserSettingsDtoList()),
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
                        outMessage.setText(CurrencyRateMessageBuilder.getRateByUserSettings(userId));
                        SendMessage startMenuMessage = new MenuCreationService().getStartMenu(userId);
                        execute(outMessage);
                        execute(startMenuMessage);
                        break;
                    case "GET_INFO":
                        startMenuMessage = new MenuCreationService().getStartMenu(userId);
                        outMessage.setText("This bot tracks exchange rates from multiple banks and provides information on request" +
                                " or automatically according to a specified schedule.\n\n" +
                                "To view rates, select \"Get actual currency\" from the main menu.\n\n" +
                                "Use the \"User settings\" section to configure the bot's operating mode.");
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
                        CurrencyRateMessageBuilder.updateUserSettings(userId, user -> user.setCurrentBank(Bank.MONOBANK));
                        bankMenuMassage = new MenuCreationService().getBankMenu(userId, messageId);
                        execute(bankMenuMassage);
                        break;
                    case "SET_NBU":
                        CurrencyRateMessageBuilder.updateUserSettings(userId, user -> user.setCurrentBank(Bank.NBU));
                        bankMenuMassage = new MenuCreationService().getBankMenu(userId, messageId);
                        execute(bankMenuMassage);
                        break;
                    case "SET_PRIVATBANK":
                        CurrencyRateMessageBuilder.updateUserSettings(userId, user -> user.setCurrentBank(Bank.PRIVATBANK));
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
                        CurrencyRateMessageBuilder.updateUserCurrencySettings(userId, Currency.USD);
                        currencyMenuMassage = new MenuCreationService().getCurrencyMenu(userId, messageId);
                        execute(currencyMenuMassage);
                        break;
                    case "SET_EUR":
                        CurrencyRateMessageBuilder.updateUserCurrencySettings(userId, Currency.EUR);
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
                    case "SET_NOTIFICATION_TIME_09":
                        CurrencyRateMessageBuilder.updateUserSettings(userId, user -> user.setNotificationTime("9"));
                        notificationMenuMassage = new MenuCreationService().setNotificationTimeMenu(userId, messageId);
                        execute(notificationMenuMassage);
                        break;
                    case "SET_NOTIFICATION_TIME_10":
                        CurrencyRateMessageBuilder.updateUserSettings(userId, user -> user.setNotificationTime("10"));
                        notificationMenuMassage = new MenuCreationService().setNotificationTimeMenu(userId, messageId);
                        execute(notificationMenuMassage);
                        break;
                    case "SET_NOTIFICATION_TIME_11":
                        CurrencyRateMessageBuilder.updateUserSettings(userId, user -> user.setNotificationTime("11"));
                        notificationMenuMassage = new MenuCreationService().setNotificationTimeMenu(userId, messageId);
                        execute(notificationMenuMassage);
                        break;
                    case "SET_NOTIFICATION_TIME_12":
                        CurrencyRateMessageBuilder.updateUserSettings(userId, user -> user.setNotificationTime("12"));
                        notificationMenuMassage = new MenuCreationService().setNotificationTimeMenu(userId, messageId);
                        execute(notificationMenuMassage);
                        break;
                    case "SET_NOTIFICATION_TIME_13":
                        CurrencyRateMessageBuilder.updateUserSettings(userId, user -> user.setNotificationTime("13"));
                        notificationMenuMassage = new MenuCreationService().setNotificationTimeMenu(userId, messageId);
                        execute(notificationMenuMassage);
                        break;
                    case "SET_NOTIFICATION_TIME_14":
                        CurrencyRateMessageBuilder.updateUserSettings(userId, user -> user.setNotificationTime("14"));
                        notificationMenuMassage = new MenuCreationService().setNotificationTimeMenu(userId, messageId);
                        execute(notificationMenuMassage);
                        break;
                    case "SET_NOTIFICATION_TIME_15":
                        CurrencyRateMessageBuilder.updateUserSettings(userId, user -> user.setNotificationTime("15"));
                        notificationMenuMassage = new MenuCreationService().setNotificationTimeMenu(userId, messageId);
                        execute(notificationMenuMassage);
                        break;
                    case "SET_NOTIFICATION_TIME_16":
                        CurrencyRateMessageBuilder.updateUserSettings(userId, user -> user.setNotificationTime("16"));
                        notificationMenuMassage = new MenuCreationService().setNotificationTimeMenu(userId, messageId);
                        execute(notificationMenuMassage);
                        break;
                    case "SET_NOTIFICATION_TIME_17":
                        CurrencyRateMessageBuilder.updateUserSettings(userId, user -> user.setNotificationTime("17"));
                        notificationMenuMassage = new MenuCreationService().setNotificationTimeMenu(userId, messageId);
                        execute(notificationMenuMassage);
                        break;
                    case "SET_NOTIFICATION_TIME_18":
                        CurrencyRateMessageBuilder.updateUserSettings(userId, user -> user.setNotificationTime("18"));
                        notificationMenuMassage = new MenuCreationService().setNotificationTimeMenu(userId, messageId);
                        execute(notificationMenuMassage);
                        break;
                    case "GET_HOME":
                        settingsMenuMessage = new MenuCreationService().getStartMenu(userId);
                        execute(settingsMenuMessage);
                        break;
                    case "OFF_NOTIFICATION":
                        CurrencyRateMessageBuilder.updateUserSettings(userId, user -> user.setNotificationTime("OFF"));
                        notificationMenuMassage = new MenuCreationService().setNotificationTimeMenu(userId, messageId);
                        execute(notificationMenuMassage);
                        break;
                    case "GET_DECIMAL_SETTINGS":
                        EditMessageText precisionMenuMessage = new MenuCreationService().getDecimalPlacesMenu(userId, messageId);
                        execute(precisionMenuMessage);
                        break;
                    case "SET_PRECISION_2":
                        CurrencyRateMessageBuilder.updateUserSettings(userId, user -> user.setDecimalPlaces(2));
                        precisionMenuMessage = new MenuCreationService().getDecimalPlacesMenu(userId, messageId);
                        execute(precisionMenuMessage);
                        break;
                    case "SET_PRECISION_3":
                        CurrencyRateMessageBuilder.updateUserSettings(userId, user -> user.setDecimalPlaces(3));
                        precisionMenuMessage = new MenuCreationService().getDecimalPlacesMenu(userId, messageId);
                        execute(precisionMenuMessage);
                        break;
                    case "SET_PRECISION_4":
                        CurrencyRateMessageBuilder.updateUserSettings(userId, user -> user.setDecimalPlaces(4));
                        precisionMenuMessage = new MenuCreationService().getDecimalPlacesMenu(userId, messageId);
                        execute(precisionMenuMessage);
                        break;
                }
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return resourceBundle.getString("bot_username");
    }

    @Override
    public String getBotToken() {
        return resourceBundle.getString("bot_token");
    }
}


