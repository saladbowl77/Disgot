package work.saladbowl.disgot.api;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import work.saladbowl.disgot.Disgot;

public class json_db {
    public static String getMcid2Discord(String mcid){
        String resDiscordID = "NotFound.";
        String[] resGetUUID = mojang.getUUID(mcid);
        String get_uuid = resGetUUID[0];
        try {
            BufferedReader jsonFile = new BufferedReader(new FileReader(Disgot.plugin.jsonFile));
            JsonArray jsonArr = JsonParser.parseReader(jsonFile).getAsJsonArray();
            for (JsonElement jsonEle : jsonArr) {
                JsonObject mcid_uuid_discord = jsonEle.getAsJsonObject();
                String json_uuid = mcid_uuid_discord.get("uuid").getAsString();
                String json_discordid = mcid_uuid_discord.get("discord").getAsString();
                if (get_uuid.equals(json_uuid)) {
                    jsonFile.close();
                    return json_discordid;
                }
            }
            jsonFile.close();
        } catch (Exception e) {
            resDiscordID = "Error. Can't read mcid-discordid-db.json.";
        }
        return resDiscordID;
    }


    public static ArrayList<String> getDiscord2Mcid(String discordID){
        ArrayList<String> mcidArr = new ArrayList<>();
        try {
            BufferedReader jsonFile = new BufferedReader(new FileReader(Disgot.plugin.jsonFile));
            JsonArray jsonArr = JsonParser.parseReader(jsonFile).getAsJsonArray();
            for (JsonElement jsonEle : jsonArr) {
                JsonObject mcid_uuid_discord = jsonEle.getAsJsonObject();
                String json_uuid = mcid_uuid_discord.get("uuid").getAsString();
                String json_discordid = mcid_uuid_discord.get("discord").getAsString();
                if (discordID.contains(json_discordid)) {
                    String resUserData = mojang.getUserName(json_uuid);
                    mcidArr.add(resUserData);
                }
            }
            jsonFile.close();
        } catch (Exception e) {
            mcidArr.add("Error. Can't read mcid-discordid-db.json.");
        }
        return mcidArr;
    }

    public static String getDiscord2UUID(String discordID) {
        String resMcid = "NotFound.";
        try {
            BufferedReader jsonFile = new BufferedReader(new FileReader(Disgot.plugin.jsonFile));
            JsonArray jsonArr = JsonParser.parseReader(jsonFile).getAsJsonArray();
            for (JsonElement jsonEle : jsonArr) {
                JsonObject mcid_uuid_discord = jsonEle.getAsJsonObject();
                String json_uuid = mcid_uuid_discord.get("uuid").getAsString();
                String json_discordid = mcid_uuid_discord.get("discord").getAsString();

                if (discordID.contains(json_discordid)) {
                    jsonFile.close();
                    return json_uuid;
                }
            }
            jsonFile.close();
        } catch (Exception e) {
            resMcid = "Error. Can't read mcid-discordid-db.json.";
        }
        return resMcid;
    }
}