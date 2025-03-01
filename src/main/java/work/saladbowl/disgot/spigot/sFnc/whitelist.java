package work.saladbowl.disgot.spigot.sFnc;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.UUID;

import com.google.gson.JsonElement;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import work.saladbowl.disgot.Config;
import work.saladbowl.disgot.Disgot;
import work.saladbowl.disgot.api.mojang;
import work.saladbowl.disgot.api.json_db;

public class whitelist {
    public static String add(String mc_id, String dc_id){
        String uuid = mojang.getUUID(mc_id)[0];
        if(uuid.equals("error")) return "Can't get UUID of Player:"+mc_id+". Please check your Player name again.\nプレイヤー:"+mc_id+"のUUIDが見つかりませんでした。プレイヤー名を再度確認してください。";

        OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(uuid));

        if(player.isWhitelisted()) return "That Player is already exist in white list!\nそのプレイヤーはすでにホワイトリストに存在しています。";
        if(dc_id.equals("") && !Config.WHITELIST_MULTIPLE && dbSearch(dc_id)) return "すでにホワイトリストに追加されているユーザーです。複数のDiscordアカウントでの登録はできません。";

        writeWhitelist(mc_id,uuid);
        //player.setWhitelisted(true);
        //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "whitelist add " + mc_id);
        if(!dc_id.equals("")) writeUserDB(mc_id,uuid,dc_id);

        Bukkit.reloadWhitelist();
        return "Success! Player has added to white list!\n正常にホワイトリストへ追加しました。";
    }

    public static Integer remove(String id){
        String mc_uuid = id;
        if (id.replace(" ", "").matches("<@[0-9]{18,19}>")){
            mc_uuid = json_db.getDiscord2UUID(id);
            if (mc_uuid.equals("NotFound.")) return 1;
        } else if(id.length() <= 16) {
            mc_uuid = mojang.getUUID(id)[0];
        }
        if(mc_uuid.equals("error")) return 0;

        OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(mc_uuid));

        if(!player.isWhitelisted()) return 1;

        //removeWhitelist(mc_uuid);
        player.setWhitelisted(false);
        //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "whitelist remove " + mc_id);
        removeMCDiscordDB(mc_uuid);

        Bukkit.reloadWhitelist();
        return 2;
    }


    public static void writeWhitelist(String name, String uuid){
        try {
            // whitelist.json 読み込み
            BufferedReader jsonFile = new BufferedReader(new FileReader("./whitelist.json"));
            JsonArray jsonArr = JsonParser.parseReader(jsonFile).getAsJsonArray();
            StringBuilder inputBuffer = new StringBuilder();

            // 該当ユーザーの配列をなくし、書き込み用の変数に代入
            JsonObject addJson = JsonParser.parseString("{\"uuid\":\""+uuid+"\",\"name\":\""+name+"\"}").getAsJsonObject();
            jsonArr.add(addJson);
            inputBuffer.append(jsonArr);
            jsonFile.close();

            // ホワリスJSON アップデート
            FileOutputStream fileOut = new FileOutputStream("./whitelist.json");
            fileOut.write(inputBuffer.toString().getBytes());
            fileOut.close();
            // ホワリス再読み込み
            Bukkit.reloadWhitelist();
        } catch (Exception e) {
            Bukkit.getLogger().warning("Can't read whitelist.json." + e);
        }
    }

    public static void writeUserDB(String name, String uuid, String discordid){
        try {
            // whitelist.json 読み込み
            BufferedReader jsonFile = new BufferedReader(new FileReader(Disgot.plugin.jsonFile));
            JsonArray jsonArr = JsonParser.parseReader(jsonFile).getAsJsonArray();
            StringBuilder inputBuffer = new StringBuilder();

            // 該当ユーザーの配列をなくし、書き込み用の変数に代入
            JsonObject addJson = JsonParser.parseString("{\"uuid\":\""+uuid+"\",\"name\":\""+name+"\",\"discord\":"+discordid +"}").getAsJsonObject();
            jsonArr.add(addJson);
            inputBuffer.append(jsonArr);
            jsonFile.close();

            // ホワリスJSON アップデート
            FileOutputStream fileOut = new FileOutputStream(Disgot.plugin.jsonFile);
            fileOut.write(inputBuffer.toString().getBytes());
            fileOut.close();
            // ホワリス再読み込み
            Bukkit.reloadWhitelist();
        } catch (Exception e) {
            Bukkit.getLogger().warning("Can't read mcid-discordid-db.json." + e);
        }
    }

    /*
    public static int removeWhitelist(String delMinecraftUUID){
        try {
            // whitelist.json 読み込み
            BufferedReader jsonFile = new BufferedReader(new FileReader("./whitelist.json"));
            JsonArray jsonArr = JsonParser.parseReader(jsonFile).getAsJsonArray();
            StringBuilder inputBuffer = new StringBuilder();

            for (JsonElement jsonEle : jsonArr) {
                JsonObject mcid_uuid_discord = jsonEle.getAsJsonObject();
                String json_uuid = mcid_uuid_discord.get("uuid").getAsString();
                if (json_uuid.equals(delMinecraftUUID)) {
                    String resUserData = mojang.getUserName(json_uuid);
                    if (resUserData.equals("NotFound.") && resUserData.equals("Error. Can't read whitelist.json.")) {
                        // error1
                        jsonFile.close();
                        return 1;
                    } else {
                        // 該当ユーザーの配列をなくし、書き込み用の変数に代入
                        jsonArr.remove(jsonEle);
                        inputBuffer.append(jsonArr);
                        jsonFile.close();
                        // ホワリスJSON アップデート
                        FileOutputStream fileOut = new FileOutputStream("./whitelist.json");
                        fileOut.write(inputBuffer.toString().getBytes());
                        fileOut.close();
                        // ホワリス再読み込み
                        Bukkit.reloadWhitelist();
                        return 2;
                    }
                }
            }
            jsonFile.close();
        } catch (Exception e) {
            return 3;
        }
        return 0;
    }
    */

    public static int removeMCDiscordDB(String delMinecraftUUID){
        try {
            // whitelist.json 読み込み
            BufferedReader jsonFile = new BufferedReader(new FileReader(Disgot.plugin.jsonFile));
            JsonArray jsonArr = JsonParser.parseReader(jsonFile).getAsJsonArray();
            StringBuilder inputBuffer = new StringBuilder();

            for (JsonElement jsonEle : jsonArr) {
                JsonObject mcid_uuid_discord = jsonEle.getAsJsonObject();
                String json_uuid = mcid_uuid_discord.get("uuid").getAsString();
                if (json_uuid.equals(delMinecraftUUID)) {
                    String resUserData = mojang.getUserName(json_uuid);
                    if (resUserData.equals("NotFound.") && resUserData.equals("Error. Can't read mcid-discordid-db.json.")) {
                        // error1
                        jsonFile.close();
                        return 1;
                    } else {
                        // 該当ユーザーの配列をなくし、書き込み用の変数に代入
                        jsonArr.remove(jsonEle);
                        inputBuffer.append(jsonArr);
                        jsonFile.close();
                        // ホワリスJSON アップデート
                        FileOutputStream fileOut = new FileOutputStream(Disgot.plugin.jsonFile);
                        fileOut.write(inputBuffer.toString().getBytes());
                        fileOut.close();
                        // ホワリス再読み込み
                        Bukkit.reloadWhitelist();
                        return 2;
                    }
                }
            }
            jsonFile.close();
        } catch (Exception e) {
            return 3;
        }
        return 0;
    }

    public static boolean whitelistSearch(String id){
        String[] resGetUUID = mojang.getUUID(id);
        String get_uuid = resGetUUID[0];

        for (OfflinePlayer player : Bukkit.getWhitelistedPlayers()) {
            if (player.getUniqueId().toString().equals(get_uuid)) {
                return true;
            }
        }
        return false;

        /*
        try {
            BufferedReader jsonFile = new BufferedReader(new FileReader("./whitelist.json"));
            JsonArray jsonArr = JsonParser.parseReader(jsonFile).getAsJsonArray();
            for (JsonElement jsonEle : jsonArr) {
                JsonObject mcid_uuid_discord = jsonEle.getAsJsonObject();
                String json_uuid = mcid_uuid_discord.get("uuid").getAsString();
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
        */
    }

    public static boolean dbSearch(String input_discord_id){
        try {
            BufferedReader jsonFile = new BufferedReader(new FileReader(Disgot.plugin.jsonFile));
            JsonArray jsonArr = JsonParser.parseReader(jsonFile).getAsJsonArray();
            for (JsonElement jsonEle : jsonArr) {
                JsonObject mcid_uuid_discord = jsonEle.getAsJsonObject();
                String json_discord_id = mcid_uuid_discord.get("discord").getAsString();
                if (input_discord_id.equals(json_discord_id)) {
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
