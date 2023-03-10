package work.saladbowl.disgot.discord;

import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jetbrains.annotations.NotNull;

import work.saladbowl.disgot.Config;
import work.saladbowl.disgot.MessageSync;
import work.saladbowl.disgot.spigot.sEvent;
import work.saladbowl.disgot.spigot.whitelist;
import org.bukkit.Bukkit;

public class dEvent extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if(e.getAuthor().isBot()) return;
        if(e.getChannel().getId().equals(Config.MESSAGE_SYNC_CHANNEL)){
            String guild = e.getGuild().getName();
            String name = e.getAuthor().getName();
            String message = e.getMessage().getContentDisplay();
            if (message.equals("")){
                String imageUrl = e.getMessage().getAttachments().get(0).getUrl();
                MessageSync.SyncMessage2spiUrl(guild,name,imageUrl,imageUrl);
            } else {
                MessageSync.SyncMessage2spi(guild,name,message);
            }
        }
        else if(e.getChannel().getId().equals(Config.WHITELIST_CHANNEL)){
            String message = e.getMessage().getContentDisplay();
            e.getChannel().sendMessage(whitelist.addWhitelist(message)).setMessageReference(e.getMessage()).queue();
        }
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {
        if(e.getChannel().getId().equals(Config.MESSAGE_SYNC_CHANNEL)) {
            if (e.getName().equals("playerlist")) {
                // コマンド送信者に対して、その人にだけ見えるメッセージとして返信する。
                String replyText = sEvent.getPlayers();
                e.reply(replyText).setEphemeral(true).queue();
            }
        }
    }

    @Override
    public void onReady(ReadyEvent e) {
        MessageSync.sendMessage2disc(Config.SI_STAT_MESS);
        Bukkit.getLogger().info("Disgotが起動しました。");
    }
}
