package work.saladbowl.disgot;

import com.google.gson.JsonObject;
import org.bukkit.Bukkit;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Webhook;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

import work.saladbowl.disgot.discord.discmain;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MessageSync {
    private static volatile String webhookUrl = null;
    private static final HttpClient httpClient = HttpClient.newHttpClient();

    public static void initWebhook() {
        TextChannel channel = getTextChannel();
        if (channel == null) return;

        channel.retrieveWebhooks().queue(webhooks -> {
            Webhook existing = webhooks.stream()
                .filter(w -> w.getOwner() != null &&
                             "Disgot".equals(w.getName()) &&
                             w.getOwner().getIdLong() == discmain.jda.getSelfUser().getIdLong())
                .findFirst().orElse(null);

            if (existing != null) {
                webhookUrl = existing.getUrl();
                Bukkit.getLogger().info("[Disgot] Webhook ready");
            } else {
                channel.createWebhook("Disgot").queue(w -> {
                    webhookUrl = w.getUrl();
                    Bukkit.getLogger().info("[Disgot] Webhook created");
                });
            }
        }, err -> Bukkit.getLogger().warning("[Disgot] Webhook init failed (MANAGE_WEBHOOKS required): " + err.getMessage()));
    }

    private static void sendWebhookMessage(String username, String content, String avatarUrl) {
        JsonObject json = new JsonObject();
        json.addProperty("content", content);
        json.addProperty("username", username);
        json.addProperty("avatar_url", avatarUrl);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(webhookUrl))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
            .build();
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.discarding());
    }

    public static void SyncMessage2spi(String guild, String name, String message){
        Bukkit.getLogger().info("[Disgot] server:" + guild + " user:" + name + " message:" + message);
        if(!Config.MT_DISCORD_ONLY.equals(message.substring(0,1))){
            String sendMessage = Config.MT_D2M.replace("&{UserName}",name).replace("&{message}",message);
            Bukkit.broadcastMessage(sendMessage);
        }
    }

    public static void SyncMessage2spiUrl(String guild, String name, String messageText, String messageLink){
        Bukkit.getLogger().info("[Disgot] server:" + guild + " user:" + name + " message:" + messageText);
        TextComponent message_sendLink = new TextComponent(messageText);
        message_sendLink.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, messageLink));

        String sendUser = Config.MT_D2M.replace("&{UserName}",name).replace("&{message}","");
        TextComponent message_sendUser = new TextComponent(sendUser);

        Bukkit.spigot().broadcast(message_sendUser, message_sendLink);
    }


    public static void SyncMessage2disc(String name, String message) {
        if (getTextChannel() == null || Config.MT_MINECRAFT_ONLY.equals(message.substring(0, 1))) return;
        if (webhookUrl != null) {
            sendWebhookMessage(name, message, "https://minotar.net/helm/" + name);
        } else {
            String sendMessage = Config.MT_M2D.replace("&{UserName}", name).replace("&{message}", message);
            getTextChannel().sendMessage(sendMessage).queue();
        }
    }


    public static void sendMessage2disc(String message){
        if(getTextChannel()!=null) getTextChannel().sendMessage(message).queue();
    }

    public static void sendMessage2discEmbed(EmbedBuilder embed){
        if(getTextChannel()!=null)getTextChannel().sendMessageEmbeds(embed.build()).queue();
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
        if (Config.STATUS_SYNC_BOOL){
            try{
                return discmain.jda.getVoiceChannelById(Config.STATUS_SYNC_CHANNEL);
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
