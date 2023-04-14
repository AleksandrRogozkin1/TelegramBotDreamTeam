package org.example.telegram.menu;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

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

    public SendMessage getBankMenu(long chatId) {
        return SendMessage.builder()
                .chatId(chatId)
                .text("Choose Bank")
                .replyMarkup(keyboardCreationService.getBankKeyboard())
                .build();
    }

    public SendMessage getCurrencyMenu(long chatId) {
        return SendMessage.builder()
                .chatId(chatId)
                .messageId(toIntExact(messageId))
                .text("Decimal places")
                .replyMarkup(keyboardCreationService.getDecimalPlacesKeyboard())
                .build();
    }

    public EditMessageText getCurrencyMenu(long chatId, long messageId) {
        return EditMessageText.builder()
                .chatId(chatId)
                .messageId(toIntExact(messageId))
                .text("Choose Currency")
                .replyMarkup(keyboardCreationService.getCurrencyKeyboard())
                .build();
    }
}
