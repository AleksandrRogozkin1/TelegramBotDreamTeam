package org.example.telegram.menu;

import org.example.telegram.CurrencyRateMessageBuilder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.User;
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
                .text("Settings:")
                .replyMarkup(keyboardCreationService.getSettingsKeyboard())
                .build();
    }

    public SendMessage getAutoRateMenu(long chatId) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(CurrencyRateMessageBuilder.getRateByUserSettings(chatId))
                .replyMarkup(keyboardCreationService.getMainKeyboard())
                .build();
    }

    public EditMessageText getBankMenu(long chatId, long messageId) {
        return EditMessageText.builder()
                .chatId(chatId)
                .messageId(toIntExact(messageId))
                .text("Select a bank:")
                .replyMarkup(keyboardCreationService.getBankKeyboard(chatId))
                .build();
    }

    public EditMessageText getDecimalPlacesMenu(long chatId, long messageId) {
        return EditMessageText.builder()
                .chatId(chatId)
                .messageId(toIntExact(messageId))
                .text("Decimal places:")
                .replyMarkup(keyboardCreationService.setDecimalPlacesKeyboard(chatId))
                .build();
    }

    public EditMessageText getCurrencyMenu(long chatId, long messageId) {
        return EditMessageText.builder()
                .chatId(chatId)
                .messageId(toIntExact(messageId))
                .text("Select a currency:")
                .replyMarkup(keyboardCreationService.getCurrencyKeyboard(chatId))
                .build();
    }

    public EditMessageText setNotificationTimeMenu(long chatId, long messageId) {
        return EditMessageText.builder()
                .chatId(chatId)
                .messageId(toIntExact(messageId))
                .text("Select a time for the notification:")
                .replyMarkup(keyboardCreationService.setNotificationTimeKeyboard(chatId))
                .build();
    }
}
