import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.util.logging.Logger;

/**
 * Created by time2die on 06.11.16.
 */
@SpringBootApplication

public class Main {
    private static final Logger logger = Logger.getLogger("tRussianBank");

    private final String tgKey;

    Main() {
        tgKey = initConfiguration();
        initTgListener();
    }

    private String initConfiguration() {
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
        new Main();
    }

    String command1 = "/cmd1";
    String command2 = "/cmd2";
    String command3 = "/cmd3";
//    String commandx = "cmdx" ;

    String ansver1 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
    String ansver2 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
    String ansver3 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";

    public class MyAmazingBot extends TelegramLongPollingBot {
        @Override
        public void onUpdateReceived(Update update) {
            if (containsComand(update, command1)) sendText(update, ansver1);
            if (containsComand(update, command2)) sendText(update, ansver2);
            if (containsComand(update, command3)) sendText(update, ansver3);
        }

        public MyAmazingBot() {
            super();
        }

        void sendText(Update update, String text) {
            SendMessage message = new SendMessage()
                    .setChatId(update.getMessage().getChatId())
                    .setText(text);

            try {
                sendMessage(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        boolean containsComand(Update update, String command) {
            return (update.hasMessage() && update.getMessage().hasText() && update.getMessage().getText().startsWith(command));
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

