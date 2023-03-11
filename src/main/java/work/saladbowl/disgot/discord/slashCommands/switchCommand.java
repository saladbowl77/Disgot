package work.saladbowl.disgot.discord.slashCommands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import work.saladbowl.disgot.spigot.sFnc.player;

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
        }
    }
}