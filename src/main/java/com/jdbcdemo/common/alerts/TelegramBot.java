package com.jdbcdemo.common.alerts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final Logger logger = LoggerFactory.getLogger(TelegramBot.class);
    private static final String TELEGRAM_URL = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s";

    @Value("${application.name}")
    private String applicationName;

    @Autowired
    private WebClient webClient;

    public TelegramBot() {
        super(BotToken);
    }

    private static final String BotToken = "6775596011:AAGGZaLOveanSy-3wgg0n0oMS4_Jtqp_PQo";
    private static final String ChatId = "-4051449332";

    @Override
    public String getBotUsername() {
        return "SpringBotAlertBot";
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println( "==> received updated, text = " + update.getMessage().getText());
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendDocument document) {
        return super.executeAsync(document);
    }

    public void sendMessage( String text) {
        push(text);
    }

    @Async
    public void sendMessageAsync( Object payload) {

        var message = new HashMap<String, Object>() {
            { { put("serviceName", applicationName); put("message", payload);} }
        };

        var request = new HashMap<String, Object>() {
            { {put("text", message);} }
        };

        push(request);
    }

    private void push( Object payload ) {

        var url = String.format(
            "https://api.telegram.org/bot%s/sendMessage?chat_id=%s",
            BotToken,
            ChatId);

        webClient
            .post()
            .uri(url)
            .bodyValue(payload)
            .retrieve()
            .toEntity(String.class)
            .block();
    }
}
