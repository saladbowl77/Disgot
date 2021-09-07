package work.saladbowl.disgot;

import net.dv8tion.jda.api.entities.TextChannel;
import work.saladbowl.disgot.discord.discmain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class MessageSync {

    public static void SyncMessage2spi(String guild, String name, String message){
        Bukkit.broadcastMessage("<" + name + "> " + message );
    }


    public static void SyncMessage2disc(String name,String message) {
        if(getTextChannel()!=null){
            getTextChannel().sendMessage("<" + name + "> " + message).queue();
        }
    }


    public static void sendMessage2disc(String message){
        if(getTextChannel()!=null){
            getTextChannel().sendMessage(message).queue();
        }
    }


    public static TextChannel getTextChannel(){
        try{
            TextChannel textChannel = discmain.jda.getTextChannelById(Config.MESSAGE_SYNC_CHANNEL);
            if(textChannel.canTalk()) {
                return textChannel;
            }else{
                Bukkit.getLogger().warning("BOT doesn't have permission. (BOTが発言権限を持っていません)");
                return null;
            }
        }
        catch (Exception e) {
            Bukkit.getLogger().warning("Cannot find such channel ID. (そのIDのチャンネルは見つかりませんでした) : "+Config.MESSAGE_SYNC_CHANNEL);
            return null;
        }
    }
}
