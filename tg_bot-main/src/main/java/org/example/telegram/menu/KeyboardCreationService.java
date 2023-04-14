package org.example.telegram.menu;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class KeyboardCreationService {
    public InlineKeyboardMarkup getMainKeyboard() {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        // Створення списку з кнопками
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        /*
            До кожної з кнопок привязаний ключ (Для Get actual currency: - GET_CURRENCY).
            Реалізовано для обробки нажаття кнопки (в CurrencyBot)
        */
        rowsInline.add(createButton("Get actual currency", "GET_CURRENCY"));
        rowsInline.add(createButton("User settings", "GET_SETTINGS"));
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    public InlineKeyboardMarkup getSettingsKeyboard() {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(createButton("Currency", "GET_CURRENCY_SETTINGS"));
        rowsInline.add(createButton("Bank", "GET_BANK_SETTINGS"));
        rowsInline.add(createButton("Precision", "GET_PRECISION_SETTINGS"));
        rowsInline.add(createButton("Notification time", "GET_NOTIFICATION_SETTINGS"));
        rowsInline.add(createButton("◀️Back", "GET_SETTINGS_BACK"));
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    public InlineKeyboardMarkup getBankKeyboard() {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add((createButton("MonoBank", "SET_MONOBANK")));
        rowsInline.add((createButton("NBU", "SET_NBU")));
        rowsInline.add((createButton("PrivatBank", "SET_PRIVATBANK")));
        rowsInline.add((createButton("◀️Back", "GET_BANK_BACK")));
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    // Ivan
    public InlineKeyboardMarkup getCurrencyKeyboard() {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add((createButton("USD", "SET_USD")));
        rowsInline.add((createButton("EUR", "SET_EUR")));
        rowsInline.add((createButton("◀️Back", "GET_CURRENCY_BACK")));
        rowsInline.add(createButton("Home", "GET_HOME"));
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    public InlineKeyboardMarkup getDecimalPlacesKeyboard() {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(createButton("2", "SET_2_DECIMAL"));
        rowsInline.add(createButton("3", "SET_3_DECIMAL"));
        rowsInline.add(createButton("4", "SET_4_DECIMAL"));
        rowsInline.add(createButton("◀️Back", "GET_CURRENCY_BACK"));
        rowsInline.add(createButton("Home", "GET_HOME"));
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    // Метод для створення кнопок
    private List<InlineKeyboardButton> createButton(String command, String callBack) {
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        InlineKeyboardButton button = InlineKeyboardButton.builder()
                .text(command)
                .callbackData(callBack)
                .build();
        rowInline.add(button);
        return rowInline;
    }
}

    private String checkMarkForBank(long userId, Bank bankName) {
        Bank userBankSetting = getUserBankSetting(userId);
        return userBankSetting == bankName ? bankName + "✅" : bankName.name();
    }

    private String checkMarkForCurrency(long userId, Currency currencyName) {
        Currency userCurrencySetting = getUserCurrencySetting(userId);
        return userCurrencySetting == currencyName ? currencyName + "✅" : currencyName.name();
    }

    /*
    private String checkMarkForTime(long userId,) {
        Bank userTimeSetting = getUserBankSetting(userId);
        return userTimeSetting == bankName ? bankName + "✅" : bankName.name();
    }

    private Bank getUserTimeSetting(long userId) {
        return FileUtils.getUserSettingsDtoList().stream()
                .filter(user -> user.getUserId() == userId)
                .map(User::getCurrentBank)
                .findFirst()
                .orElse(Bank.PRIVATBANK);
    }
    */

    private Bank getUserBankSetting(long userId) {
        return FileUtils.getUserSettingsDtoList().stream()
                .filter(user -> user.getUserId() == userId)
                .map(User::getCurrentBank)
                .findFirst()
                .orElse(Bank.PRIVATBANK);
    }

    private Currency getUserCurrencySetting(long userId) {
        return FileUtils.getUserSettingsDtoList().stream()
                .filter(userSettings -> userSettings.getUserId() == userId)
                .map(User::getCurrentCurrency)
                .findFirst()
                .orElse(org.example.currency.Currency.USD);
    }

//    private String getUserNotificationSetting(long userId) {
//        return FileUtils.getUserSettingsDtoList().stream()
//                .filter(userSettings -> userSettings.getUserId() == userId)
//                .map(User::getNotificationTime)
//                .map(time -> time.equals("OFF") ? time + "" : time +"✅" )
//                .collect(Collectors.joining());
//    }
}
