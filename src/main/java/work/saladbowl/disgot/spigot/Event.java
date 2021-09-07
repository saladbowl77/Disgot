package work.saladbowl.disgot.spigot;

import work.saladbowl.disgot.Disgot;
import work.saladbowl.disgot.MessageSync;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.Material;

public class Event  implements Listener {
    public static Disgot plugin;
    public Event(Disgot instance) { plugin = instance; }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e){
        String playerName = e.getPlayer().getDisplayName();
        String message = e.getMessage();
        MessageSync.SyncMessage2disc(playerName,message);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        String playerName = e.getPlayer().getDisplayName();
        MessageSync.sendMessage2disc(playerName + " がゲームに参加しました!いらっしゃい!");
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        String playerName = e.getPlayer().getDisplayName();
        MessageSync.sendMessage2disc(playerName + " がゲームを退出しました。ばいばーい!また来てね");
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        e.getDeathMessage();
        MessageSync.sendMessage2disc(e.getDeathMessage());
    }

    @EventHandler
    public void onPlayerChat(PlayerCommandPreprocessEvent e){
        String playerName = e.getPlayer().getDisplayName();
        String message = e.getMessage();
        MessageSync.sendMessage2disc(playerName + "がコマンドを使用した! : " + message);
    }

    @EventHandler
    public void onPlayerJoin(BlockBreakEvent e) {
        String playerName = e.getPlayer().getDisplayName();
        if (e.getBlock().getType() == Material.DIAMOND_ORE || e.getBlock().getType() == Material.DEEPSLATE_DIAMOND_ORE ) {
            MessageSync.sendMessage2disc(playerName + "がダイヤモンドを見つけた!");
        } else if (e.getBlock().getType() == Material.EMERALD_ORE || e.getBlock().getType() == Material.DEEPSLATE_EMERALD_ORE) {
            MessageSync.sendMessage2disc(playerName + "がエメラルドを見つけた!");
        }
    }
}
