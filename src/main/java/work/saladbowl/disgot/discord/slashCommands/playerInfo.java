package work.saladbowl.disgot.discord.slashCommands;

import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import work.saladbowl.disgot.api.mojang;
import work.saladbowl.disgot.spigot.sFnc.player;
import work.saladbowl.disgot.spigot.sFnc.whitelist;

public class playerInfo {
    public static void reply(SlashCommandInteractionEvent e, String infoId){
        if (infoId.length() > 16){
            infoId = mojang.getUserName(infoId);
        }
        Map<String, Object> playerData = player.getPlayerInfo(infoId);
        String sendDescription;
        String onlineType = "参加状況 : 🔴 オフライン";

        EmbedBuilder eb = new EmbedBuilder();

        if (whitelist.whitelistSearch(infoId)){
            if (playerData != null) {
                onlineType = "参加状況 : 🟢 オンライン";

                String healthText = "";
                double healthDouble = (double) playerData.get("health") / 2;
                int healthInt = (int) healthDouble;
                for (int i = 0; i < healthInt; i++) healthText += "❤️";
                if (healthDouble - (double) healthInt < 0.5) healthText += "💔️";
                else healthText += "❤️";
                for (int i =0; i < 10 - healthInt; i++) healthText += "♡";

                Date first_day_date = new Date();
                first_day_date.setTime((long) playerData.get("first"));
                String first_day_str = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(first_day_date);

                Date last_day_date = new Date();
                last_day_date.setTime((long) playerData.get("last"));
                String last_day_str = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(last_day_date);

                eb
                    .addField("ディメンション", String.valueOf(playerData.get("world")),false)
                    .addField("座標X",    String.valueOf(playerData.get("location_X")), true)
                    .addField("座標Y",  String.valueOf(playerData.get("location_Y")), true)
                    .addField("座標Z",  String.valueOf(playerData.get("location_Z")), true)
                    .addField("体力",   healthText, true)
                    .addField("食糧",   String.valueOf(playerData.get("food")), true)
                    .addField("レベル", playerData.get("level") + "次のレベルまで" + playerData.get("exp_to_level"), true)
                    .addField("Ping",  String.valueOf(playerData.get("ping")), false)
                    .addField("最初にサーバーへ入った日時", first_day_str, true)
                    .addField("最後ににサーバーへ入った日時", last_day_str, true);
            }
            sendDescription = "ホワイトリスト : ⭕️ 追加済み\n" + onlineType;
        } else {
            sendDescription = "ホワイトリスト : ❌ 未追加";
        }
        eb.setTitle(infoId+"さんの情報")
            .setDescription(sendDescription)
            .setThumbnail("https://minotar.net/armor/body/" + infoId + "/200.png");

        e.replyEmbeds(eb.build()).setEphemeral(true).queue();
    }
}