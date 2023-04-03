package work.saladbowl.disgot.discord;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.*;
import net.dv8tion.jda.api.requests.GatewayIntent;

import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import work.saladbowl.disgot.Config;

import java.util.ArrayList;
import java.util.List;

public class discmain{
    public static JDA jda;
    public static void launch() throws LoginException {
        try {
            // Login 処理
            JDABuilder builder = JDABuilder.createLight(Config.TOKEN, GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.DIRECT_MESSAGES)
                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .addEventListeners(new dEvent())
                    .setActivity(Activity.playing(Config.BOT_STATUS));
            jda = builder.build();

            // ログインが完了するまで待つ
            jda.awaitReady();

            // 参加しているサーバーを ID から取得
            Guild guild = jda.getGuildById(Config.SERVER_ID);

            List<SlashCommandData> commands = new ArrayList<>();

            // 登録するコマンドを作成
            // プレイヤー数のコマンド
            SlashCommandData playerList = Commands.slash("playerlist", "サーバー内の人数を取得するコード");
            SlashCommandData playerInfo = Commands.slash("playerinfo", "サーバーに入っているプレイヤーの情報を取得するコマンド")
                    .addOptions(
                            new OptionData(OptionType.STRING, "id", "マインクラフトのIDを記入してください", true)
                    );
            commands.add(playerList);
            commands.add(playerInfo);

            if (Config.WHITELIST_JG_BOOL && Config.WHITELIST_JG_TYPE.equals("CMD")) {
                SlashCommandData joinGame = Commands.slash("joingame", "Minecraftサーバーに参加するためのコマンド")
                        .addOptions(
                                new OptionData(OptionType.STRING, "mcid", "マインクラフトのIDを記入してください", true)
                        );
                commands.add(joinGame);
            }
            if (Config.WHITELIST_CMD_BOOL) {
                // ホワイトリストに追加するコマンド
                SlashCommandData addWhitelist = Commands.slash("whitelist", "ユーザーをホワイトリストに追加します")
                        .addSubcommands(
                                new SubcommandData("add", "ホワイトリストに追加します。")
                                        .addOptions(
                                                new OptionData(OptionType.STRING, "mcid", "マイクラIDを記載するところです。", true),
                                                new OptionData(OptionType.USER, "discordid", "ディスコードのIDを記載するところです。", Config.WHITELIST_CMD_ALLOW_MC_ONLY)
                                        ),
                                new SubcommandData("remove", "ホワイトリストから削除します。")
                                        .addOptions(
                                                new OptionData(OptionType.STRING, "id", "マインクラフトまたはDiscordのIDを記載するところです", true)
                                        )
                        );
                commands.add(addWhitelist);
            }
            guild.updateCommands().addCommands(commands).queue();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
