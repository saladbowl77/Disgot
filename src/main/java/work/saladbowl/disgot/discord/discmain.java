package work.saladbowl.disgot.discord;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.*;
import net.dv8tion.jda.api.requests.GatewayIntent;

import work.saladbowl.disgot.Config;

public class discmain{
    public static JDA jda;
    public static void launch() throws LoginException {
        try {
            // Login 処理
            JDABuilder builder = JDABuilder.createLight(Config.TOKEN, GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.DIRECT_MESSAGES)
                    .addEventListeners(new dEvent())
                    .setActivity(Activity.playing(Config.BOT_STATUS));
            jda = builder.build();

            // ログインが完了するまで待つ
            jda.awaitReady();

            // 参加しているサーバーを ID から取得
            Guild guild = jda.getGuildById(Config.SERVER_ID);

            // 登録するコマンドを作成
            // プレイヤー数のコマンド
            SlashCommandData playerList = Commands.slash("playerlist", "サーバー内の人数を取得するコード");
            SlashCommandData playerInfo = Commands.slash("playerinfo", "サーバーに入っているプレイヤーの情報を取得するコマンド")
                    .addOptions(
                            new OptionData(OptionType.STRING, "id", "マインクラフトのIDを記入してください", true)
                    );

            // コマンドを指定したサーバーに登録
            guild.updateCommands()
                    .addCommands(playerList)
                    .addCommands(playerInfo)
                    .queue();

        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
