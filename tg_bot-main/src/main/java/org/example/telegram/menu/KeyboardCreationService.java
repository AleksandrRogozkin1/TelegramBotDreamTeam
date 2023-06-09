package org.example.telegram.menu;

import org.example.currency.Bank;
import org.example.currency.Currency;
import org.example.telegram.CurrencyRateMessageBuilder;
import org.example.user.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class KeyboardCreationService {
    public InlineKeyboardMarkup getMainKeyboard() {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(createButton("Get actual currency", "GET_CURRENCY"));
        rowsInline.add(createButton("User settings", "GET_SETTINGS"));
        rowsInline.add(createButton("ℹ️Information", "GET_INFO"));
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    public InlineKeyboardMarkup getSettingsKeyboard() {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(createButton("Currency", "GET_CURRENCY_SETTINGS"));
        rowsInline.add(createButton("Bank", "GET_BANK_SETTINGS"));
        rowsInline.add(createButton("Precision", "GET_DECIMAL_SETTINGS"));
        rowsInline.add(createButton("Notification time", "GET_NOTIFICATION_SETTINGS"));
        rowsInline.add(createButton("◀️Back", "GET_SETTINGS_BACK"));
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    public InlineKeyboardMarkup getBankKeyboard(long userId) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(createButton(checkMarkForBank(userId, Bank.MONOBANK), "SET_MONOBANK"));
        rowsInline.add(createButton(checkMarkForBank(userId, Bank.NBU), "SET_NBU"));
        rowsInline.add(createButton(checkMarkForBank(userId, Bank.PRIVATBANK), "SET_PRIVATBANK"));
        rowsInline.add(createButton("◀️Back", "GET_BANK_BACK"));
        rowsInline.add(createButton("\uD83C\uDFE0Home", "GET_HOME"));
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    public InlineKeyboardMarkup setNotificationTimeKeyboard(long userId) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
        List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
        List<InlineKeyboardButton> rowInline3 = new ArrayList<>();
        List<InlineKeyboardButton> rowInline4 = new ArrayList<>();
        List<InlineKeyboardButton> rowInline5 = new ArrayList<>();
        rowInline1.add(InlineKeyboardButton.builder().text(checkMarkForNotificationTime(userId, "09:00")).callbackData("SET_NOTIFICATION_TIME_09").build());
        rowInline1.add(InlineKeyboardButton.builder().text(checkMarkForNotificationTime(userId, "10:00")).callbackData("SET_NOTIFICATION_TIME_10").build());
        rowInline1.add(InlineKeyboardButton.builder().text(checkMarkForNotificationTime(userId, "11:00")).callbackData("SET_NOTIFICATION_TIME_11").build());
        rowInline2.add(InlineKeyboardButton.builder().text(checkMarkForNotificationTime(userId, "12:00")).callbackData("SET_NOTIFICATION_TIME_12").build());
        rowInline2.add(InlineKeyboardButton.builder().text(checkMarkForNotificationTime(userId, "13:00")).callbackData("SET_NOTIFICATION_TIME_13").build());
        rowInline2.add(InlineKeyboardButton.builder().text(checkMarkForNotificationTime(userId, "14:00")).callbackData("SET_NOTIFICATION_TIME_14").build());
        rowInline3.add(InlineKeyboardButton.builder().text(checkMarkForNotificationTime(userId, "15:00")).callbackData("SET_NOTIFICATION_TIME_15").build());
        rowInline3.add(InlineKeyboardButton.builder().text(checkMarkForNotificationTime(userId, "16:00")).callbackData("SET_NOTIFICATION_TIME_16").build());
        rowInline3.add(InlineKeyboardButton.builder().text(checkMarkForNotificationTime(userId, "17:00")).callbackData("SET_NOTIFICATION_TIME_17").build());
        rowInline4.add(InlineKeyboardButton.builder().text(checkMarkForNotificationTime(userId, "18:00")).callbackData("SET_NOTIFICATION_TIME_18").build());
        rowInline4.add(InlineKeyboardButton.builder().text(checkMarkForNotificationTime(userId,"OFF")).callbackData("OFF_NOTIFICATION").build());
        rowInline4.add(InlineKeyboardButton.builder().text("◀️Back").callbackData("GET_NOTIFICATION_BACK").build());
        rowInline5.add(InlineKeyboardButton.builder().text("\uD83C\uDFE0Home").callbackData("GET_HOME").build());
        rowsInline.add(rowInline1);
        rowsInline.add(rowInline2);
        rowsInline.add(rowInline3);
        rowsInline.add(rowInline4);
        rowsInline.add(rowInline5);
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    public InlineKeyboardMarkup setDecimalPlacesKeyboard(long userId) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(createButton(checkMarkForDecimalPlaces(userId, 2), "SET_PRECISION_2"));
        rowsInline.add(createButton(checkMarkForDecimalPlaces(userId, 3), "SET_PRECISION_3"));
        rowsInline.add(createButton(checkMarkForDecimalPlaces(userId, 4), "SET_PRECISION_4"));
        rowsInline.add(createButton("◀️Back", "GET_SETTINGS"));
        rowsInline.add(createButton("\uD83C\uDFE0Home", "GET_HOME"));
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    public InlineKeyboardMarkup getCurrencyKeyboard(long userId) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add((createButton(checkMarkForCurrency(userId, Currency.USD), "SET_USD")));
        rowsInline.add((createButton(checkMarkForCurrency(userId, Currency.EUR), "SET_EUR")));
        rowsInline.add((createButton("◀️Back", "GET_CURRENCY_BACK")));
        rowsInline.add(createButton("\uD83C\uDFE0Home", "GET_HOME"));
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    private List<InlineKeyboardButton> createButton(String command, String callBack) {
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        InlineKeyboardButton button = InlineKeyboardButton.builder()
                .text(command)
                .callbackData(callBack)
                .build();
        rowInline.add(button);
        return rowInline;
    }

    private String checkMarkForBank(long userId, Bank bankName) {
        Bank userBankSetting = getUserBankSetting(userId);
        return userBankSetting == bankName ? bankName + "✅" : bankName.name();
    }

    private String checkMarkForCurrency(long userId, Currency currencyName) {
        List<Currency> userCurrencySetting = getUserCurrencySetting(userId);
        return userCurrencySetting.contains(currencyName) ? currencyName + "✅" : currencyName.name();
    }

    private String checkMarkForDecimalPlaces(long userId, int decimalPlaces) {
        int userDecimalPlaces = getUserDecimalSetting(userId);
        return userDecimalPlaces == decimalPlaces ? decimalPlaces + "✅" : String.valueOf(decimalPlaces);
    }

    private String checkMarkForNotificationTime(long userId, String notificationTime) {
        String formattedNotificationTime;
        String userNotificationTime = getNotificationTime(userId);
        if (notificationTime.equals("10:00")) {
            formattedNotificationTime = notificationTime.substring(0, notificationTime.indexOf(":"));
            return userNotificationTime.equals(formattedNotificationTime) ? notificationTime + "✅" : notificationTime;
        }
        else if(notificationTime.equals("OFF")) {
            return userNotificationTime.equals("OFF") ? notificationTime + "✅" : notificationTime;
        }
        formattedNotificationTime = notificationTime
                .replaceAll("0", "")
                .replaceAll(":", "");

        return userNotificationTime.equals(formattedNotificationTime) ? notificationTime + "✅" : notificationTime;
    }


    private Bank getUserBankSetting(long userId) {
        return CurrencyRateMessageBuilder.getUserSettingsDtoList().stream()
                .filter(user -> user.getUserId() == userId)
                .map(User::getCurrentBank)
                .findFirst()
                .orElse(Bank.PRIVATBANK);
    }

    private String getNotificationTime(long userId) {
        return CurrencyRateMessageBuilder.getUserSettingsDtoList().stream()
                .filter(user -> user.getUserId() == userId)
                .map(User::getNotificationTime)
                .findFirst()
                .orElse("OFF");
    }

    private int getUserDecimalSetting(long userId) {
        return CurrencyRateMessageBuilder.getUserSettingsDtoList().stream()
                .filter(user -> user.getUserId() == userId)
                .map(User::getDecimalPlaces)
                .findFirst()
                .orElse(2);
    }

    private List<Currency> getUserCurrencySetting(long userId) {
        return CurrencyRateMessageBuilder.getUserSettingsDtoList().stream()
                .filter(userSettings -> userSettings.getUserId() == userId)
                .map(User::getCurrentCurrency)
                .findFirst()
                .orElse(List.of(Currency.USD));
    }
}