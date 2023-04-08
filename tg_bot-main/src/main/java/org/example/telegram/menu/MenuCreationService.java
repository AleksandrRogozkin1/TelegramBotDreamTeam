package org.example.telegram.menu;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

public class MenuCreationService extends BotCommand {
    private static final KeyboardCreationService keyboardCreationService = new KeyboardCreationService();

    public SendMessage getStartMenu(long chatId) {
        return SendMessage.builder()
                .chatId(chatId)
                .text("How can I help you?")
                .replyMarkup(keyboardCreationService.getMainKeyboard())
                .build();
    }
}