import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import org.telegram.telegrambots.abilitybots.api.objects.MessageContext;
import org.telegram.telegrambots.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.abilitybots.api.bot.BaseAbilityBot;



import org.mockito.Mockito;


import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MiBotTest {

    public static final long USER_ID = 1337L;
    public static final long CHAT_ID = 1337L;

    private MiBot miBot;
    private SilentSender silent;

    @Mock
    private TelegramClient mockTelegramClient;

    @Mock
    private Update mockUpdate;

    @Mock
    private User mockUser;

    @Mock
    private BaseAbilityBot mockBot;

    @BeforeEach
    void setUp() {
        // Inicializar el bot con un token simulado
        miBot = spy(new MiBot("7075157328:AAHgeGeAA7oSzSrntasoQLMhDQht25SOwEg"));
        //miBot.onRegister();
        silent = mock(SilentSender.class);
        miBot.setSilentSender(silent);
        mockUpdate = mock(Update.class);
        mockUser =  new User( USER_ID, "Adan", false);
        mockBot = mock(BaseAbilityBot.class); 

    }


    @Test
    public void canSayHelloWorld() {
        Update upd = new Update();
        // Create a new User - User is a class similar to Telegram User
        System.out.println("*********** A ");
        User user = new User( USER_ID, "Adan", false);
        user.setLanguageCode("es");
        // This is the context that you're used to, it is the necessary conumer item for the ability
        System.out.println("*********** B ");
        MessageContext context = MessageContext.newContext(upd, mockUser, CHAT_ID, mockBot, new String[]{}); // Se agregan los parámetros correctos
        System.out.println("*********** C ");



        // We consume a context in the lamda declaration, so we pass the context to the action logic
        miBot.saysHelloWorld().action().accept(context);
        System.out.println("*********** D ");
        // We verify that the silent sender was called only ONCE and sent Hello World to CHAT_ID
        // The silent sender here is a mock!
        Mockito.verify(silent, times(1)).send("Hello World!", CHAT_ID);
        System.out.println("*********** E ");
    }
}