package work.saladbowl.disgot.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MinecraftLang {
    private static volatile Map<String, String> langMap = null;

    public static void load(String langCode) {
        try {
            String mcVersion = Bukkit.getBukkitVersion().split("-")[0]; // e.g., "1.21.1"

            // バージョンマニフェストからバージョンURLを取得
            JsonObject manifest = fetchJson("https://launchermeta.mojang.com/mc/game/version_manifest.json");
            String versionUrl = null;
            for (JsonElement v : manifest.getAsJsonArray("versions")) {
                JsonObject ver = v.getAsJsonObject();
                if (ver.get("id").getAsString().equals(mcVersion)) {
                    versionUrl = ver.get("url").getAsString();
                    break;
                }
            }
            if (versionUrl == null) {
                Bukkit.getLogger().warning("[Disgot] Mojang version manifest: version " + mcVersion + " not found");
                return;
            }

            // バージョンメタデータからアセットインデックスURLを取得
            JsonObject versionMeta = fetchJson(versionUrl);
            String assetIndexUrl = versionMeta.getAsJsonObject("assetIndex").get("url").getAsString();

            // アセットインデックスから言語ファイルのハッシュを取得
            JsonObject assetIndex = fetchJson(assetIndexUrl);
            String langKey = "minecraft/lang/" + langCode + ".json";
            JsonObject objects = assetIndex.getAsJsonObject("objects");
            if (!objects.has(langKey)) {
                Bukkit.getLogger().warning("[Disgot] Language '" + langCode + "' not found in asset index");
                return;
            }
            String hash = objects.getAsJsonObject(langKey).get("hash").getAsString();
            String langUrl = "https://resources.download.minecraft.net/" + hash.substring(0, 2) + "/" + hash;

            // 言語ファイルをダウンロードしてキャッシュ
            JsonObject langJson = fetchJson(langUrl);
            Map<String, String> map = new HashMap<>();
            for (Map.Entry<String, JsonElement> entry : langJson.entrySet()) {
                map.put(entry.getKey(), entry.getValue().getAsString());
            }
            langMap = Collections.unmodifiableMap(map);
            Bukkit.getLogger().info("[Disgot] Language '" + langCode + "' loaded (" + map.size() + " entries)");
        } catch (Exception e) {
            Bukkit.getLogger().warning("[Disgot] Failed to load language file: " + e.getMessage());
        }
    }

    // 翻訳済み文字列を返す。未ロード時は null を返す（呼び出し側でフォールバック）
    public static String translate(Component component) {
        if (langMap == null) return null;
        return resolveComponent(component);
    }

    private static String resolveComponent(Component c) {
        if (c instanceof TranslatableComponent tc) {
            String key = tc.key();
            String template = langMap.getOrDefault(key, key);
            List<? extends ComponentLike> args = tc.args();
            for (int i = 0; i < args.size(); i++) {
                template = template.replace("%" + (i + 1) + "$s", resolveComponent(args.get(i).asComponent()));
            }
            return template;
        }
        return PlainTextComponentSerializer.plainText().serialize(c);
    }

    private static JsonObject fetchJson(String urlStr) throws Exception {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new URL(urlStr).openStream()))) {
            return JsonParser.parseReader(in).getAsJsonObject();
        }
    }
}
