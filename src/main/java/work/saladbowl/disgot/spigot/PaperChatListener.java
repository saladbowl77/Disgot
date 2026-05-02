package work.saladbowl.disgot.spigot;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import work.saladbowl.disgot.Disgot;
import work.saladbowl.disgot.MessageSync;

public class PaperChatListener implements Listener {
    public PaperChatListener(Disgot instance) {}

    @EventHandler
    public void onPlayerChat(AsyncChatEvent e) {
        String playerName = e.getPlayer().getName();
        String message = PlainTextComponentSerializer.plainText().serialize(e.message());
        MessageSync.SyncMessage2disc(playerName, message);
    }
}
