package work.saladbowl.disgot;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import work.saladbowl.disgot.discord.discmain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;

public class MessageSync {

    public static void SyncMessage2spi(String guild, String name, String message){
        Bukkit.getLogger().info(guild + name + message);
        if(!Config.MT_DISCORD_ONLY.equals(message.substring(0,1))){
            String sendMessage = Config.MT_D2M.replace("&{UserName}",name).replace("&{message}",message);
            Bukkit.broadcastMessage(sendMessage);
        }
    }

    public static void SyncMessage2spiUrl(String guild, String name, String messageText, String messageLink){
        TextComponent message_send = new TextComponent(messageText);
        message_send.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, messageLink));

        String sendUser = Config.MT_M2D.replace("&{UserName}",name).replace("&{message}","");

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(sendUser);
            player.spigot().sendMessage(message_send);
        }
    }


    public static void SyncMessage2disc(String name,String message) {
        if(getTextChannel()!=null && !Config.MT_MINECRAFT_ONLY.equals(message.substring(0,1))){
            String sendMessage = Config.MT_M2D.replace("&{UserName}",name).replace("&{message}",message);
            getTextChannel().sendMessage(sendMessage).queue();
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
            Bukkit.getLogger().warning("Cannot find such channel ID. (そのIDのテキストチャンネルは見つかりませんでした) : "+Config.MESSAGE_SYNC_CHANNEL);
            return null;
        }
    }

    public static VoiceChannel getServerStatusChannel(){
        if (Config.STATUS_SYNC_BOOL.equals("true")){
            try{
                VoiceChannel voiceChannel = discmain.jda.getVoiceChannelById(Config.STATUS_SYNC_CHANNEL);
                return voiceChannel;
            }
            catch (Exception e) {
                Bukkit.getLogger().warning("Cannot find such channel ID. (そのIDのボイスチャンネルは見つかりませんでした) : "+Config.STATUS_SYNC_CHANNEL);
                return null;
            }
        } else {
            return null;
        }
    }
}
