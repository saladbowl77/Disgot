package work.saladbowl.disgot;

import work.saladbowl.disgot.discord.discmain;
import work.saladbowl.disgot.spigot.sEvent;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;

public final class Disgot extends JavaPlugin {

    public static Disgot plugin = null;

    @Override
    public void onEnable() {
        plugin = this;
        getServer().getPluginManager().registerEvents(new sEvent(this), this);
        saveDefaultConfig();
        Config.load();
        getLogger().info("Hello!");
        try {
            discmain.launch();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("Goodbye!");
        MessageSync.sendMessage2disc(Config.SI_CLOSE_MESS);
        discmain.jda.shutdown();
    }
}
