package org.example.telegram.menu;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import static java.lang.Math.toIntExact;

public class MenuCreationService extends BotCommand {
    private static final KeyboardCreationService keyboardCreationService = new KeyboardCreationService();

    public SendMessage getStartMenu(long chatId) {
        return SendMessage.builder()
                .chatId(chatId)
                .text("Hi! How can I help you?")
                .replyMarkup(keyboardCreationService.getMainKeyboard())
                .build();
    }

    public SendMessage getSettingsMenu(long chatId) {
        return SendMessage.builder()
                .chatId(chatId)
                .text("Settings")
                .replyMarkup(keyboardCreationService.getSettingsKeyboard())
                .build();
    }

    public EditMessageText getBankMenu(long chatId, long messageId) {
        return EditMessageText.builder()
                .chatId(chatId)
                .messageId(toIntExact(messageId))
                .text("Choose Bank")
                .replyMarkup(keyboardCreationService.getBankKeyboard(chatId))
                .build();
    }

    public EditMessageText getDecimalPlacesMenu(long chatId, long messageId) {
        return EditMessageText.builder()
                .chatId(chatId)
                .messageId(toIntExact(messageId))
                .text("Decimal places")
                .replyMarkup(keyboardCreationService.getDecimalPlacesKeyboard(chatId))
                .build();
    }

    public EditMessageText getCurrencyMenu(long chatId, long messageId) {
        return EditMessageText.builder()
                .chatId(chatId)
                .messageId(toIntExact(messageId))
                .text("Choose Currency")
                .replyMarkup(keyboardCreationService.getCurrencyKeyboard(chatId))
                .build();
    }

    public EditMessageText setNotificationTimeMenu(long chatId, long messageId) {
        return EditMessageText.builder()
                .chatId(chatId)
                .messageId(toIntExact(messageId))
                .text("Choose Time for notification")
                .replyMarkup(keyboardCreationService.setNotificationTimeKeyboard())
                .build();
    }
}
