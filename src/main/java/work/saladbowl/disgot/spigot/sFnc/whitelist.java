package work.saladbowl.disgot.spigot.sFnc;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.UUID;

import work.saladbowl.disgot.api.mojang;

public class whitelist {
    public static String addWhitelist(String name){
        String uuid = mojang.getUUID(name)[0];
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

    public static boolean whitelistSearch(String id){
        String[] resGetUUID = mojang.getUUID(id);
        String get_uuid = resGetUUID[0];

        try {
            BufferedReader jsonFile = new BufferedReader(new FileReader("./whitelist.json"));
            JsonArray jsonArr = new JsonParser().parse(jsonFile).getAsJsonArray();
            for (int i = 0; i < jsonArr.size(); i++) {
                JsonObject mcid_uuid_discord = jsonArr.get(i).getAsJsonObject();
                String json_uuid = mcid_uuid_discord.get("uuid").toString().replaceAll("\"", "");
                if (get_uuid.equals(json_uuid)) {
                    jsonFile.close();
                    return true;
                }
            }
            jsonFile.close();
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
