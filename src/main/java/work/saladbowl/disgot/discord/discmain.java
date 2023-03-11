package work.saladbowl.disgot.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

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
            SlashCommandData playerList = Commands.slash("playerlist", "サーバー内の人数を取得するコード");

            // コマンドを指定したサーバーに登録
            guild.updateCommands()
                    .addCommands(playerList)
                    .queue();

        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
