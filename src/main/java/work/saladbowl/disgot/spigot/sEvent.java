package work.saladbowl.disgot.spigot;

import org.bukkit.Bukkit;
import work.saladbowl.disgot.Disgot;
import work.saladbowl.disgot.Config;
import work.saladbowl.disgot.MessageSync;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.Material;

import java.lang.reflect.Array;

public class sEvent implements Listener {
    public static Disgot plugin;
    public static Integer JOIN_PLAYERS;
    public static Integer MAX_PLAYERS;
    public static StringBuilder PLAYERS_LIST;

    public sEvent(Disgot instance) { plugin = instance; }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e){
        String playerName = e.getPlayer().getDisplayName();
        String message = e.getMessage();
        MessageSync.SyncMessage2disc(playerName,message);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if (Config.UI_USER_NOTICE_BOOL.equals("true")) {
            String playerName = e.getPlayer().getDisplayName();
            MessageSync.sendMessage2disc(playerName + Config.UI_JOIN_MESS);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        if (Config.UI_USER_NOTICE_BOOL.equals("true")) {
            String playerName = e.getPlayer().getDisplayName();
            MessageSync.sendMessage2disc(playerName + Config.UI_LEAVE_MESS);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        e.getDeathMessage();
        MessageSync.sendMessage2disc(e.getDeathMessage());
    }

    @EventHandler
    public void onPlayerChat(PlayerCommandPreprocessEvent e){
        if (Config.CMD_NOTICE_BOOL.equals("true")){
            String playerName = e.getPlayer().getDisplayName();
            String message = e.getMessage();
            MessageSync.sendMessage2disc(playerName + message);
        }
    }

    @EventHandler
    public void onPlayerJoin(BlockBreakEvent e) {
        if (Config.ORE_GET_NOTICE_MINECRAFT.equals("true") || Config.ORE_GET_NOTICE_DISCORD.equals("true")) {
            String playerName = e.getPlayer().getDisplayName();
            if (e.getBlock().getType() == Material.DIAMOND_ORE || e.getBlock().getType() == Material.DEEPSLATE_DIAMOND_ORE) {
                if (Config.ORE_GET_NOTICE_MINECRAFT.equals("true")) Bukkit.broadcastMessage(playerName + "がダイヤモンドを見つけた!");
                if (Config.ORE_GET_NOTICE_DISCORD.equals("true")) MessageSync.sendMessage2disc(playerName + "がダイヤモンドを見つけた!");
            } else if (e.getBlock().getType() == Material.EMERALD_ORE || e.getBlock().getType() == Material.DEEPSLATE_EMERALD_ORE) {
                if (Config.ORE_GET_NOTICE_MINECRAFT.equals("true")) Bukkit.broadcastMessage(playerName + "がエメラルドを見つけた!");
                if (Config.ORE_GET_NOTICE_DISCORD.equals("true")) MessageSync.sendMessage2disc(playerName + "がエメラルドを見つけた!");
            }
        }
    }

    @EventHandler
    public void getBedEnterResult(PlayerBedEnterEvent e){
        if(Config.BED_SLEEP_NOTICE.equals("true")) {
            if (e.getBedEnterResult() == PlayerBedEnterEvent.BedEnterResult.OK) {
                String playerName = e.getPlayer().getDisplayName();
                Bukkit.broadcastMessage(playerName + "は寝ようとしている...");
            }
        }
    }

    public static String getPlayers(){
        JOIN_PLAYERS = Bukkit.getOnlinePlayers().toArray().length;
        MAX_PLAYERS = Bukkit.getMaxPlayers();
        StringBuilder sb = new StringBuilder("");
        for (Player p : Bukkit.getOnlinePlayers()) {sb.append("- ").append(p.getName()).append("\n");}
        PLAYERS_LIST = sb;

        Bukkit.getLogger().warning(JOIN_PLAYERS + ":" + MAX_PLAYERS + ":" + PLAYERS_LIST);

        return "現在のプレイヤー数は" + JOIN_PLAYERS + "/" + MAX_PLAYERS + "\n" + PLAYERS_LIST;
    }
}
