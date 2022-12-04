package com.dhivakar.quotegeneratorbot.event.model;

import com.dhivakar.quotegeneratorbot.model.UserCommand;
import lombok.Builder;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.InputStream;

public class RandomImageEvent extends BaseEvent {

    @Builder
    public RandomImageEvent(String chatID, Update update, UserCommand userCommand) {
        super(chatID, update, userCommand);
    }

    public SendPhoto defaultMessage(InputStream image) {

        InputFile file = new InputFile(image, "Test Name");

        return SendPhoto.builder()
                .chatId(getChatID())
                .photo(file)
                .build();
    }


}
