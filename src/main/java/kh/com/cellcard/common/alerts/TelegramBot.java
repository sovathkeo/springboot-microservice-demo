package kh.com.cellcard.common.alerts;

import kh.com.cellcard.common.configurations.appsetting.ApplicationConfiguration;
import kh.com.cellcard.common.wrapper.WebClientWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
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

    @Autowired
    private ApplicationConfiguration appSetting;

    @Autowired
    private WebClientWrapper webClientWrapper;

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

        var request = new HashMap<String, Object>() {
            { {put("text", new HashMap<String, Object>() {
                { { put("serviceName", appSetting.getApplicationName()); put("message", payload);} }
            });} }
        };

        push(request);
    }

    private void push( Object payload ) {

        var url = String.format(
            "https://api.telegram.org/bot%s/sendMessage?chat_id=%s",
            BotToken,
            ChatId);

        webClientWrapper.post(url, payload);
    }
}
