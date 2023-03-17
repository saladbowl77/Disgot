package work.saladbowl.disgot.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class mojang {
    public static String[] getUUID(String name) {
        String[] resData = new String[2];

        try {
            JsonObject resJson;
            String uuid ;
            String mc_name;

            BufferedReader in = new BufferedReader(new InputStreamReader(new URL("https://api.mojang.com/users/profiles/minecraft/" + name).openStream()));
            resJson = JsonParser.parseReader(in).getAsJsonObject();

            uuid = resJson.get("id")
                    .toString()
                    .replaceAll("\"", "")
                    .replaceAll("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5");
            mc_name = resJson.get("name")
                    .toString()
                    .replaceAll("\"", "");

            resData[0] = uuid;
            resData[1] = mc_name;
            in.close();
        } catch (Exception e) {
            resData[0] = "error";
            resData[1] = "error";
        }
        return resData;
    }

    public static String getUserName(String uuid) {
        String userName = "";
        try {
            BufferedReader resJson = new BufferedReader(new InputStreamReader(new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid).openStream()));
            JsonObject jsonObj = JsonParser.parseReader(resJson).getAsJsonObject();
            userName = jsonObj.get("name").toString().replaceAll("\"", "");
            resJson.close();
        } catch (Exception e) {
            userName = "error";
        }
        return userName;
    }
}