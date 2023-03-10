package work.saladbowl.disgot;

import org.bukkit.plugin.java.JavaPlugin;
import work.saladbowl.disgot.discord.discmain;
import work.saladbowl.disgot.spigot.sEvent;

import javax.security.auth.login.LoginException;

public final class Disgot extends JavaPlugin {

    public static Disgot plugin = null;

    @Override
    public void onEnable() {
        plugin = this;
        getServer().getPluginManager().registerEvents(new sEvent(this),this);
        saveDefaultConfig();
        Config.load();
        getLogger().info("Hello!");
        try {
            discmain.launch();
        } catch (LoginException e) {
            e.printStackTrace();
        }

        try {
            /*
            //チャンネルの概要変更
            MessageSync
                    .getTextChannel()
                    .getManager()
                    .setTopic(":green_circle: サーバー起動中 プレイヤー数:0人")
                    .queue();
             */
            //VCのチャンネル名変更
            MessageSync
                    .getServerStatusChannel()
                    .getManager()
                    .setName(Config.STATUS_SYNC_ENABLE_TEXT)
                    .queue();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        try {
            /*
            //チャンネルの概要変更
            MessageSync
                    .getTextChannel()
                    .getManager()
                    .setTopic(":red_circle: サーバー停止中")
                    .queue();
             */
            //VCのチャンネル名変更
            MessageSync
                    .getServerStatusChannel()
                    .getManager()
                    .setName(Config.STATUS_SYNC_DISABLE_TEXT)
                    .queue();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        MessageSync.sendMessage2disc(Config.SI_CLOSE_MESS);
        discmain.jda.shutdown();
        getLogger().info("Goodbye!");
    }
}
