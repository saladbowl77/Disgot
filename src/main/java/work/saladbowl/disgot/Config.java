package work.saladbowl.disgot;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    public static Disgot plugin;
    public Config(Disgot instance) { plugin = instance; }

    public static String TOKEN;
    public static String MESSAGE_SYNC_CHANNEL;
    public static String WHITELIST_CHANNEL;

    public static void load() {
        FileConfiguration config = Disgot.plugin.getConfig();
        TOKEN = config.getString("TOKEN");
        MESSAGE_SYNC_CHANNEL = config.getString("MESSAGE_SYNC_CHANNEL");
        WHITELIST_CHANNEL = config.getString("WHITELIST_CHANNEL");
    }
}

