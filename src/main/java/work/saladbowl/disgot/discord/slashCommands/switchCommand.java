package work.saladbowl.disgot.discord.slashCommands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import work.saladbowl.disgot.Config;
import work.saladbowl.disgot.spigot.sFnc.player;
import work.saladbowl.disgot.spigot.sFnc.whitelist;

public class switchCommand {
    public static void swich(SlashCommandInteractionEvent e) {
        switch (e.getName()){
            case "playerlist":
                // コマンド送信者に対して、その人にだけ見えるメッセージとして返信する。
                String replyText = player.getPlayers();
                e.reply(replyText).setEphemeral(true).queue();
            case "playerinfo":
                playerInfo.reply(e, e.getOption("id").getAsString());
                break;
            case "joingame":
                String reply = whitelist.add(e.getOption("mcid").getAsString(), e.getUser().getId());
                e.reply(reply).setEphemeral(false).queue();
                break;
            case "whitelist":
                System.out.println(Config.WHITELIST_CMD_CHANNEL);
                if(Config.WHITELIST_CMD_CHANNEL.equals("XXXXXXXXXXX") || e.getChannel().getId().equals(Config.WHITELIST_CMD_CHANNEL)) {
                    switch (e.getSubcommandName()) {
                        case "add":
                            String wa_mcid = e.getOption("mcid").getAsString();
                            String wa_discordid = "";
                            if (e.getOption("discordid") != null) wa_discordid = e.getOption("discordid").getAsString();
                            whitelist_cmd.add(e, wa_mcid, wa_discordid);
                            break;
                        case "remove":
                            String remove_id = e.getOption("id").getAsString();
                            whitelist_cmd.remove(e, remove_id);
                            break;
                    }
                } else {
                    e.reply("現在のチャンネルまたはあなたのロールではホワイトリストに追加することはできません。").setEphemeral(false).queue();
                }
        }
    }
}