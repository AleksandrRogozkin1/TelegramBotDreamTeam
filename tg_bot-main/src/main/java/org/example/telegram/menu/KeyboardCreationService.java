package org.example.telegram.menu;

import org.example.currency.Bank;
import org.example.currency.Currency;
import org.example.user.User;
import org.example.utils.FileUtils;
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

    public InlineKeyboardMarkup getBankKeyboard(long userId) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add((createButton(checkMarkForBank(userId,Bank.MONOBANK), "SET_MONOBANK")));
        rowsInline.add((createButton(checkMarkForBank(userId,Bank.NBU), "SET_NBU")));
        rowsInline.add((createButton(checkMarkForBank(userId,Bank.PRIVATBANK), "SET_PRIVATBANK")));
        rowsInline.add((createButton("◀️Back", "GET_BANK_BACK")));
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    public InlineKeyboardMarkup setNotificationTimeKeyboard() {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add((createButton("09:00", "SET_TIME_9")));
        rowsInline.add((createButton("10:00", "SET_TIME_10")));
        rowsInline.add((createButton("11:00", "SET_TIME_11")));
        rowsInline.add((createButton("12:00", "SET_TIME_12")));
        rowsInline.add((createButton("13:00", "SET_TIME_13")));
        rowsInline.add((createButton("14:00", "SET_TIME_14")));
        rowsInline.add((createButton("15:00", "SET_TIME_15")));
        rowsInline.add((createButton("16:00", "SET_TIME_16")));
        rowsInline.add((createButton("17:00", "SET_TIME_17")));
        rowsInline.add((createButton("18:00", "SET_TIME_18")));
        rowsInline.add((createButton("◀️Back", "GET_NOTIFICATION_BACK")));
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    // Ivan
    public InlineKeyboardMarkup getCurrencyKeyboard(long userId) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add((createButton(checkMarkForCurrency(userId, Currency.USD), "SET_USD")));
        rowsInline.add((createButton( checkMarkForCurrency(userId, Currency.EUR), "SET_EUR")));
        rowsInline.add((createButton("◀️Back", "GET_CURRENCY_BACK")));
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
    private String checkMarkForBank(long userId, Bank bankName){
        Bank userBankSetting = getUserBankSetting(userId);
        return userBankSetting==bankName ? bankName+"✅" : bankName.name();
    }
    private Bank getUserBankSetting(long userId) {
        return FileUtils.getUserSettingsDtoList().stream()
                .filter(user -> user.getUserId()==userId)
                .map(User::getCurrentBank)
                .findFirst()
                .orElse(Bank.PRIVATBANK);
    }
//    private String checkMarkForTime(long userId,){
//        Bank userTimeSetting = getUserBankSetting(userId);
//        return userTimeSetting==bankName ? bankName+"✅" : bankName.name();
//    }
//    private Bank getUserTimeSetting(long userId) {
//        return FileUtils.getUserSettingsDtoList().stream()
//                .filter(user -> user.getUserId()==userId)
//                .map(User::getCurrentBank)
//                .findFirst()
//                .orElse(Bank.PRIVATBANK);
//    }
private String checkMarkForCurrency(long userId, Currency currencyName) {
    Currency userCurrencySetting = getUserCurrencySetting(userId);
    return userCurrencySetting==currencyName ? currencyName+"✅" : currencyName.name();
}

    private Currency getUserCurrencySetting(long userId) {
        return FileUtils.getUserSettingsDtoList().stream()
                .filter(userSettings -> userSettings.getUserId() == userId)
                .map(User::getCurrentCurrency)
                .findFirst()
                .orElse(org.example.currency.Currency.USD);
    }


}
