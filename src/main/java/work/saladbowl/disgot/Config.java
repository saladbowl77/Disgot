package work.saladbowl.disgot;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    public static Disgot plugin;
    public Config(Disgot instance) { plugin = instance; }

    public static String TOKEN;
    public static String SERVER_ID;
    public static String BOT_STATUS;
    public static String MESSAGE_SYNC_CHANNEL;
    public static String WHITELIST_CHANNEL;

    public static String SI_SERVER_NOTICE_BOOL;
    public static String SI_STAT_MESS;
    public static String SI_CLOSE_MESS;

    public static String UI_USER_NOTICE_BOOL;
    public static String UI_JOIN_MESS;
    public static String UI_LEAVE_MESS;

    public static String MT_D2M;
    public static String MT_M2D;
    public static String MT_DISCORD_ONLY;
    public static String MT_MINECRAFT_ONLY;

    public static String CMD_NOTICE_BOOL;
    public static String CMD_NOTICE_MESS;

    public static String ORE_GET_NOTICE_MINECRAFT;
    public static String ORE_GET_NOTICE_DISCORD;

    public static String BED_SLEEP_NOTICE;

    public static void load() {
        FileConfiguration config = Disgot.plugin.getConfig();
        TOKEN = config.getString("TOKEN");
        SERVER_ID = config.getString("SERVER_ID");
        BOT_STATUS = config.getString("BOT_STATUS");
        MESSAGE_SYNC_CHANNEL = config.getString("MESSAGE_SYNC_CHANNEL");
        WHITELIST_CHANNEL = config.getString("WHITELIST_CHANNEL");

        SI_SERVER_NOTICE_BOOL = config.getString("SERVER_INFO.SERVER_NOTICE_BOOL");
        SI_STAT_MESS = config.getString("SERVER_INFO.STAT_MESS");
        SI_CLOSE_MESS = config.getString("SERVER_INFO.CLOSE_MESS");

        UI_USER_NOTICE_BOOL = config.getString("USER_INFO.USER_NOTICE_BOOL");
        UI_JOIN_MESS = config.getString("USER_INFO.JOIN_MESS");
        UI_LEAVE_MESS = config.getString("USER_INFO.LEAVE_MESS");

        MT_D2M = config.getString("MESSAGE_TYPE.D2M");
        MT_M2D = config.getString("MESSAGE_TYPE.M2D");
        MT_DISCORD_ONLY = config.getString("MESSAGE_TYPE.DISCORD_ONLY");
        MT_MINECRAFT_ONLY = config.getString("MESSAGE_TYPE.MINECRAFT_ONLY");

        CMD_NOTICE_BOOL = config.getString("CMD.CMD_NOTICE_BOOL");
        CMD_NOTICE_MESS = config.getString("CMD.CMD_NOTICE_MESS");

        ORE_GET_NOTICE_MINECRAFT = config.getString("ORE.GET_NOTICE.SEND_MINECRAFT");
        ORE_GET_NOTICE_DISCORD = config.getString("ORE.GET_NOTICE.SEND_DISCORD");

        BED_SLEEP_NOTICE = config.getString("BED.SLEEP_NOTICE");
    }
}

