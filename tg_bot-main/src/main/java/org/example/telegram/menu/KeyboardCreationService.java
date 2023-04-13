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
    public InlineKeyboardMarkup getCurrencyKeyboard() {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add((createButton("USD", "SET_USD")));
        rowsInline.add((createButton("EUR", "SET_EUR")));
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
}
