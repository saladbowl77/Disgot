package work.saladbowl.disgot.discord.slashCommands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import work.saladbowl.disgot.Config;
import work.saladbowl.disgot.spigot.sFnc.whitelist;

public class whitelist_cmd {
    public static void add(SlashCommandInteractionEvent e, String wa_mcid, String wa_discordid){
        String resAddDB = whitelist.add(wa_mcid, wa_discordid);
        e.reply(resAddDB).setEphemeral(Config.WHITELIST_CMD_SHOW_ALL).queue();
    }

    public static void remove(SlashCommandInteractionEvent e, String id){
        Integer resAddDB = whitelist.remove(id);
        switch (resAddDB) {
            case 0:
                e.reply(id + "のUUIDが見つかりませんでした。打ち間違えやJava Edition以外でないか確認してください。").setEphemeral(Config.WHITELIST_CMD_SHOW_ALL).queue();
                break;
            case 1:
                e.reply(id + "はホワイトリスト・DBに登録されていません。").setEphemeral(Config.WHITELIST_CMD_SHOW_ALL).queue();
                break;
            case 2:
                e.reply(id + "を正常にホワイトリスト・DBから削除しました。").setEphemeral(Config.WHITELIST_CMD_SHOW_ALL).queue();
                break;
        }
    }
}