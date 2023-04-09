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
            Це реалізовано для обробки нажаття кнопки (в CurrencyBot це реалізовано)
        */
        rowsInline.add(createButton("Get actual currency", "GET_CURRENCY"));
        rowsInline.add(createButton("User settings", "GET_SETTINGS"));
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
