import com.typesafe.config.Config;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import com.typesafe.config.ConfigFactory;

import java.util.logging.Logger;

/**
 * Created by time2die on 06.11.16.
 */
@SpringBootApplication

public class Main {
    private static final Logger logger = Logger.getLogger("tRussianBank");

    private final String tgKey ;
    Main(){
        tgKey =  initConfiguration() ;
        initTgListener() ;
    }

    private String  initConfiguration() {
        Config conf = ConfigFactory.load();
        return conf.getString("tgBotKey");
    }

    private void initTgListener() {
        try {
            TelegramBotsApi tg = new TelegramBotsApi();
            ApiContextInitializer.init();
            tg.registerBot(new MyAmazingBot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static void main(String[] args) throws TelegramApiRequestException {
        new Main() ;
    }

    public class MyAmazingBot extends TelegramLongPollingBot {
        @Override
        public void onUpdateReceived(Update update) {

//            if (update.hasMessage() && update.getMessage().hasText() && "/status".equals(update.getMessage().getText())) {
                SendMessage message = new SendMessage()
                        .setChatId(update.getMessage().getChatId())
                        .setText("АУЕ!");
                try {
                    sendMessage(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
//            }
        }

        @Override
        public String getBotUsername() {
            return "jUberBot";
        }

        @Override
        public String getBotToken() {
            return tgKey;
        }
    }
}

