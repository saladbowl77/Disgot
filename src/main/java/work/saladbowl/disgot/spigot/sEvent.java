package work.saladbowl.disgot.spigot;

import java.awt.Color;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

import net.dv8tion.jda.api.EmbedBuilder;

import work.saladbowl.disgot.Config;
import work.saladbowl.disgot.Disgot;
import work.saladbowl.disgot.MessageSync;

public class sEvent implements Listener {
    public static Disgot plugin;

    public sEvent(Disgot instance) { plugin = instance; }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e){
        String playerName = e.getPlayer().getDisplayName();
        String message = e.getMessage();
        MessageSync.SyncMessage2disc(playerName,message);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if (Config.UI_NOTICE_BOOL) {
            String playerName = e.getPlayer().getDisplayName();
            String playerUUID = e.getPlayer().getUniqueId().toString();
            String JoinMessage = Config.UI_JOIN_MESSAGE.replace("&{UserName}",playerName);
            String PlayerIcon = "https://minotar.net/helm/" + playerUUID;
            switch (Config.UI_JOIN_TYPE) {
                case "text":
                    MessageSync.sendMessage2disc(JoinMessage);
                    break;
                case "embed":
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setAuthor(JoinMessage, null, PlayerIcon);
                    String ebColor = Config.UI_JOIN_COLOR;
                    if (Config.UI_JOIN_COLOR.equals(null)) ebColor = "#37ab58";
                    eb.setColor(Color.decode(ebColor));
                    MessageSync.sendMessage2discEmbed(eb);
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        if (Config.UI_NOTICE_BOOL) {
            String playerName = e.getPlayer().getDisplayName();
            String playerUUID = e.getPlayer().getUniqueId().toString();
            String LeaveMessage = Config.UI_LEAVE_MESSAGE.replace("&{UserName}",playerName);
            String PlayerIcon = "https://minotar.net/helm/" + playerUUID;

            switch (Config.UI_LEAVE_TYPE) {
                case "text":
                    MessageSync.sendMessage2disc(LeaveMessage);
                    break;
                case "embed":
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setAuthor(LeaveMessage, null, PlayerIcon);
                    String ebColor = Config.UI_LEAVE_COLOR;
                    if (Config.UI_LEAVE_COLOR.equals(null)) ebColor = "#c44949";
                    eb.setColor(Color.decode(ebColor));
                    MessageSync.sendMessage2discEmbed(eb);
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        MessageSync.sendMessage2disc(e.getDeathMessage());
    }

    @EventHandler
    public void onPlayerChat(PlayerCommandPreprocessEvent e){
        if (Config.CMD_NOTICE_BOOL){
            String playerName = e.getPlayer().getDisplayName();
            String message = e.getMessage();
            MessageSync.sendMessage2disc(playerName + message);
        }
    }

    @EventHandler
    public void onPlayerJoin(BlockBreakEvent e) {
        if (Config.ORE_GET_NOTICE_MINECRAFT || Config.ORE_GET_NOTICE_DISCORD) {
            String playerName = e.getPlayer().getDisplayName();
            for (Map<?, ?> obj : Config.ORE_GET_NOTICE_LIST){
                Map<String, String> map = (Map<String, String>) obj;

                Material block_material = Material.matchMaterial(map.get("name"));
                if (e.getBlock().getType().equals(block_material)) {
                    String sendTxt = map.get("sendText").replace("&{UserName}",playerName);
                    if (Config.ORE_GET_NOTICE_MINECRAFT) Bukkit.broadcastMessage(sendTxt);
                    if (Config.ORE_GET_NOTICE_DISCORD) MessageSync.sendMessage2disc(sendTxt);
                    break;
                }
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
    /*
    public void updateServerInfo(){
        try {
            Integer NOW_PLAYER_COUNT = Bukkit.getOnlinePlayers().toArray().length;
            //チャンネルの概要変更
            MessageSync
                    .getTextChannel()
                    .getManager()
                    .setTopic(":green_circle: サーバー起動中 プレイヤー数:" + NOW_PLAYER_COUNT + "人")
                    .queue();
            //VCのチャンネル名変更
            MessageSync
                    .getTextChannel()
                    .getManager()
                    .setName("🟢起動中 プレイヤー数:" + NOW_PLAYER_COUNT + "人")
                    .queue();
        } catch (Exception ex) {
            Bukkit.getLogger().warning("Cannot find such channel ID. (そのIDのチャンネルは見つかりませんでした) : "+Config.MESSAGE_SYNC_CHANNEL);
        }
    }
     */
}
