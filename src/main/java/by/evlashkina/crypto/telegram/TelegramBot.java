package by.evlashkina.crypto.telegram;


import by.evlashkina.crypto.entity.CurrencyDetails;
import by.evlashkina.crypto.exception.UserException;
import by.evlashkina.crypto.service.CurrencyService;
import by.evlashkina.crypto.service.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final static int DELAY_FOR_UPDATE = 10000;

    private final BotProperties properties;
    private final UserService userService;
    private final CurrencyService currencyService;

    @Override
    public void onUpdateReceived(@NotNull Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String memberName = update.getMessage().getFrom().getFirstName();

            if (messageText.equals("/start")) {
                startBot(chatId, memberName);
            } else {
                sendNotificationToUser(chatId, "Unknown command");
                log.info("Unexpected message");
            }
        }
    }

    private void startBot(long chatId, String userName) {

        try {
            userService.registerNewUser(chatId, userName);
            sendNotificationToUser(chatId, "Hi, " + userName + "! You were successfully registered");
            currencyService.requestPriceUpdate();
        } catch (UserException ex) {
            sendNotificationToUser(chatId, "TelegramBot is unavailable right now");
            log.info("TelegramBot is unavailable right now");
        }
    }

    @Scheduled(fixedDelay = DELAY_FOR_UPDATE)
    public void sendUpdatedCurrencyPricesToUsers() {

        List<CurrencyDetails> updatedCurrencyDetails = currencyService.requestPriceUpdate();
        if (!updatedCurrencyDetails.isEmpty()) {
            notifyAllUsers(updatedCurrencyDetails);
        }
    }

    public void notifyAllUsers(List<CurrencyDetails> updatedCurrencyDetails) {

        Optional.of(userService.findAllUsers())
                .ifPresent(users -> users
                        .forEach(user -> sendNotificationToUser(user.getChatId(), updatedCurrencyDetails.toString())));
    }

    public void sendNotificationToUser(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        try {
            execute(message);
            log.info("Reply has been sent {}", text);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    public String getBotUsername() {
        return properties.getUsername();
    }

    public String getBotToken() {
        return properties.getToken();
    }
}