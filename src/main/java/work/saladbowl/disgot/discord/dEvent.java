package work.saladbowl.disgot.discord;

import java.util.ArrayList;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import org.jetbrains.annotations.NotNull;

import org.bukkit.Bukkit;

import com.vdurmont.emoji.EmojiParser;

import work.saladbowl.disgot.Config;
import work.saladbowl.disgot.MessageSync;
import work.saladbowl.disgot.discord.slashCommands.switchCommand;
import work.saladbowl.disgot.spigot.sFnc.whitelist;
import work.saladbowl.disgot.api.json_db;

public class dEvent extends ListenerAdapter {
    @Override
    public void onReady(@NotNull ReadyEvent e) {
        if (Config.SI_SERVER_NOTICE_BOOL) MessageSync.sendMessage2disc(Config.SI_STAT_MESS);
        Bukkit.getLogger().info("Disgotが起動しました。");
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if(e.getAuthor().isBot()) return;
        if(e.getChannel().getId().equals(Config.MESSAGE_SYNC_CHANNEL)){
            String guild = e.getGuild().getName();
            String name = e.getAuthor().getName();
            String message = EmojiParser.parseToAliases(e.getMessage().getContentStripped());
            if (message.equals("")){
                String imageUrl = e.getMessage().getAttachments().get(0).getUrl();
                MessageSync.SyncMessage2spiUrl(guild,name,imageUrl,imageUrl);
            } else {
                MessageSync.SyncMessage2spi(guild,name,message);
            }
        } else if(
                Config.WHITELIST_JG_BOOL &&
                Config.WHITELIST_JG_TYPE.equals("TXT") &&
                e.getChannel().getId().equals(Config.WHITELIST_JG_CHANNEL)
        ){
            e.getChannel()
                .sendMessage(whitelist.add(e.getMessage().getContentStripped(), e.getAuthor().getId()))
                .setMessageReference(e.getMessage())
                .queue();
        }
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {
        if(
                e.getChannel().getId().equals(Config.MESSAGE_SYNC_CHANNEL) ||
                e.getChannel().getId().equals(Config.WHITELIST_JG_CHANNEL) ||
                e.getChannel().getId().equals(Config.WHITELIST_CMD_CHANNEL)
        ) switchCommand.swich(e);
        else e.reply("Minecraftとのチャットチャンネルまたは指定のコマンドチャンネルで実行してください。");
    }
    @Override
    public void onGuildMemberRemove(GuildMemberRemoveEvent e) {
        ArrayList<String> searchMcidArr = json_db.getDiscord2Mcid(e.getUser().getId());
        for (String searchMcid : searchMcidArr) {
            if (searchMcid.equals("Error. Can't read mcid-discordid-db.json.")) {
                MessageSync.sendMessage2disc("Discordサーバーからユーザー(<@" + e.getUser().getId() + ">さん)が退出したため、ホワイトリストから削除しようとしましたが、DBファイルのアクセス中になんらかのエラーが発生しました。");
                return;
            }
            // Whitelistからの削除 → レスポンスコードの返却
            Integer whitelistRemove = whitelist.remove(searchMcid);

            switch (whitelistRemove){
                case 0:
                    MessageSync.sendMessage2disc("Discordサーバーからユーザー(<@" + e.getUser().getId() + ">さん)が退出したため、マインクラフトID" + searchMcid + "をホワイトリストから削除しようとしましたが、処理されませんでした。");
                    break;
                case 1:
                    MessageSync.sendMessage2disc("Discordサーバーからユーザー(<@" + e.getUser().getId() + ">さん)が退出したため、マインクラフトID" + searchMcid + "をホワイトリストから削除しようとしましたが、ユーザーが見当たらないまたは、DBファイルにアクセスできません。");
                    break;
                case 2:
                    MessageSync.sendMessage2disc("Discordサーバーからユーザー(<@" + e.getUser().getId() + ">さん)が退出し、マインクラフトID" + searchMcid + "をホワイトリストから削除しました。");
                    break;
                case 3:
                    MessageSync.sendMessage2disc("Discordサーバーからユーザー(<@" + e.getUser().getId() + ">さん)が退出したため、マインクラフトID" + searchMcid + "をホワイトリストから削除しようとしましたが、DBファイル処理中にエラーが発生しました。");
                    break;
            }
        }
    }
}
