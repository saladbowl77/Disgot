package work.saladbowl.disgot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.security.auth.login.LoginException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import work.saladbowl.disgot.discord.discmain;
import work.saladbowl.disgot.spigot.sEvent;


public final class Disgot extends JavaPlugin {

    public static Disgot plugin = null;

    @Override
    public void onEnable() {
        plugin = this;
        getServer().getPluginManager().registerEvents(new sEvent(this),this);
        saveDefaultConfig();
        Config.load();
        getLogger().info("Hello!");

        File jsonFile = new File(Config.WhitelistDBPath);
        if (jsonFile.exists()) {
            getLogger().info("ユーザーDBファイルはすでに存在しています。");
        } else {
            Path jsonFilePath = Paths.get(Config.WhitelistDBPath);
            try{
                Files.createFile(jsonFilePath);
                ArrayList<String> arr = new ArrayList<>();
                arr.add("[]");
                Files.write(jsonFilePath, arr);
                getLogger().info("ユーザーDBファイルを生成しました。");
            }catch(IOException e){
                Bukkit.getLogger().warning(e.toString());
            }
        }

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
            if (Config.STATUS_SYNC_BOOL) {
                //VCのチャンネル名変更
                MessageSync
                        .getServerStatusChannel()
                        .getManager()
                        .setName(Config.STATUS_SYNC_ENABLE_TEXT)
                        .queue();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        if (Config.STATUS_SYNC_BOOL) {
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
        }

        if (Config.SI_SERVER_NOTICE_BOOL) MessageSync.sendMessage2disc(Config.SI_CLOSE_MESS);
        discmain.jda.shutdown();
        getLogger().info("Goodbye!");
    }
}
