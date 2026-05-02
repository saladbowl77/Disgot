package work.saladbowl.disgot.spigot;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import work.saladbowl.disgot.Disgot;
import work.saladbowl.disgot.MessageSync;

public class SpigotChatListener implements Listener {
    public SpigotChatListener(Disgot instance) {}

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        String playerName = e.getPlayer().getDisplayName();
        String message = e.getMessage();
        MessageSync.SyncMessage2disc(playerName, message);
    }
}
