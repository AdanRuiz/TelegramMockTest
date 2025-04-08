import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import org.telegram.telegrambots.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.abilitybots.api.objects.Ability;
import org.telegram.telegrambots.abilitybots.api.objects.Locality;
import org.telegram.telegrambots.abilitybots.api.objects.Privacy;


public class MiBot implements LongPollingSingleThreadUpdateConsumer {

    private static final String BOT_TOKEN = "7075157328:AAHgeGeAA7oSzSrntasoQLMhDQht25SOwEg"; // Reemplaza con tu token
    private static final String BOT_USERNAME = "AgileOrganizerTesterBot"; // Reemplaza con el nombre de tu bot
    private final TelegramClient telegramClient;
    private SilentSender silentSender;

    public MiBot() {
        this(BOT_TOKEN); // Llamar al constructor correcto con solo el botToken
    }

    public MiBot(String botToken) {
        telegramClient = new OkHttpTelegramClient(botToken);
    }

    public TelegramClient getTelegramClient(){
        return telegramClient;
    }

    public String getBotUsername() {
        return BOT_USERNAME;
    }

    public String getBotToken() {
        return BOT_TOKEN;
    }

    // Setter para SilentSender
    public void setSilentSender(SilentSender silentSender) {
        this.silentSender = silentSender;
    }

    // Definir la habilidad "Hello World"
    public Ability saysHelloWorld() {
        return Ability.builder()
                .name("hello")
                .locality(Locality.valueOf("ALL"))
                .privacy(Privacy.valueOf("PUBLIC"))
                .info("Responde con 'Hello World!'")
                .input(0)
                .action(ctx -> silentSender.send("Hello World!", ctx.chatId()))
                .build();
    }

    @Override
    public void consume(Update update) {
        // Verificar si hay un mensaje de texto recibido
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            
            long chatId = update.getMessage().getChatId();
            
            // Crear el mensaje de respuesta
            SendMessage message = SendMessage.builder()
                    .chatId(chatId)
                    .text(messageText)
                    .build();

            try {
                
                if (silentSender != null) {
                    // Usar SilentSender en pruebas para evitar comunicación real
                    silentSender.send(message.getText(), chatId);
                } else {
                    telegramClient.execute(message); // Enviar mensaje si no es un test
                }
                
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}

