package work.saladbowl.disgot.spigot.sFnc;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class player {
    public static String getPlayers(){
        int JOIN_PLAYERS = Bukkit.getOnlinePlayers().toArray().length;
        int MAX_PLAYERS = Bukkit.getMaxPlayers();
        StringBuilder sb = new StringBuilder();

        for (Player p : Bukkit.getOnlinePlayers()) {
            sb.append("- ").append(p.getName()).append("\n");
        }
        return "現在のプレイヤー数は" + JOIN_PLAYERS + "/" + MAX_PLAYERS + "\n" + sb;
    }

    public static Map<String, Object> getPlayerInfo(String playerName){
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (
                    player.getDisplayName().equals(playerName)
                            || player.getUniqueId().toString().equals(playerName)
            ) {
                Map<String, Object> locations = new HashMap<>();

                locations.put("player_name",  player.getDisplayName());
                locations.put("location_X",   player.getLocation().getX());
                locations.put("location_Y",   player.getLocation().getY());
                locations.put("location_Z",   player.getLocation().getZ());
                locations.put("health",       player.getHealth());
                locations.put("level",        player.getLevel());
                locations.put("exp_to_level", player.getExpToLevel());
                locations.put("ping",         player.getPing());
                locations.put("food",         player.getFoodLevel());
                locations.put("world",        player.getWorld().getName());
                locations.put("first",        player.getFirstPlayed());
                locations.put("last",         player.getLastPlayed());

                return locations;
            }
        }
        return null;
    }
}