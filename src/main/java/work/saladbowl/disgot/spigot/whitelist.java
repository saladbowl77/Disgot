package work.saladbowl.disgot.spigot;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;

public class whitelist {

    public static String addWhitelist(String name){
        String uuid = getUUID(name);
        if(uuid.equals("error")) return "Can't get UUID of Player:"+name+". Please check your Player name again.\nプレイヤー:"+name+"のUUIDが見つかりませんでした。プレイヤー名を再度確認してください。";

        OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(uuid));

        if(player.isWhitelisted()){
            return "That Player is already exist in white list!\nそのプレイヤーはすでにホワイトリストに存在しています。";
        }

        writeWhitelist(name,uuid);

        Bukkit.reloadWhitelist();
        return "Success! Player has added to white list!\n正常にホワイトリストへ追加しました。";
    }

    public static void writeWhitelist(String name, String uuid){
        try {
            BufferedReader file = new BufferedReader(new FileReader("./whitelist.json"));
            StringBuffer inputBuffer = new StringBuffer();
            String line;

            while ((line = file.readLine()) != null) {
                if(line.contains("[]")){
                    line = "[" +
                            "\n  {" +
                            "\n    \"uuid\":\""+uuid+"\"," +
                            "\n    \"name\":\""+name+"\"" +
                            "\n  }" +
                            "\n]";
                }
                else if(line.contains("]")){
                    line = "  ," +
                            "\n  {" +
                            "\n    \"uuid\":\""+uuid+"\"," +
                            "\n    \"name\":\""+name+"\"" +
                            "\n  }" +
                            "\n]";
                }
                inputBuffer.append(line);
                inputBuffer.append('\n');
            }
            file.close();

            FileOutputStream fileOut = new FileOutputStream("./whitelist.json");
            fileOut.write(inputBuffer.toString().getBytes());
            fileOut.close();

        } catch (Exception e) {
            System.out.println("Can't read whitelist.json.");
        }
    }



    public static String getUUID(String name) {
        String uuid = "";
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new URL("https://api.mojang.com/users/profiles/minecraft/" + name).openStream()));
            uuid = (((JsonObject)new JsonParser().parse(in)).get("id")).toString().replaceAll("\"", "");
            uuid = uuid.replaceAll("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5");
            in.close();
        } catch (Exception e) {
            uuid = "error";
        }
        return uuid;
    }
}
