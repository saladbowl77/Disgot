package work.saladbowl.disgot.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import work.saladbowl.disgot.Config;

import javax.security.auth.login.LoginException;

public class discmain{
    public static JDA jda;
    public static void launch() throws LoginException {
        JDABuilder builder = JDABuilder.createDefault(Config.TOKEN).addEventListeners(new Event());
        jda = builder.build();
    }
}
