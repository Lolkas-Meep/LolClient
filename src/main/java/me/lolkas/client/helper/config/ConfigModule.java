package me.lolkas.client.helper.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.lolkas.client.funStuff.modules.Module;
import me.lolkas.client.funStuff.modules.ModuleRegistry;
import me.lolkas.client.funStuff.modules.config.DynamicValue;
import me.lolkas.client.funStuff.modules.config.keybind.KeybindManager;
import me.lolkas.client.utills.TypeConverter;
import net.minecraft.client.MinecraftClient;
import org.apache.commons.compress.utils.Charsets;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//Skidded from atomic https://github.com/cornos/Atomic

public class ConfigModule {
    final static MinecraftClient client = MinecraftClient.getInstance();
    static final List<Module> toEnable = new ArrayList<>();
    static final File MODULE_CONFIG_FILE;
    public static boolean loaded = false;
    public static boolean enabled = false;
    static final String TOP = """
            //Skidded from atomic https://github.com/cornos/Atomic
            """;

    static {
        MODULE_CONFIG_FILE = new File(client.runDirectory.getAbsolutePath() + "/lolclient/modules.amogus");
    }

    public static void saveState(){
        if(!loaded || !enabled) return;
        JsonObject base = new JsonObject();
        JsonArray enabled = new JsonArray();
        JsonArray config = new JsonArray();
        for (Module module : ModuleRegistry.getModules()){
            if(module.isEnabled()) enabled.add(module.getName());
            JsonObject currentConfig = new JsonObject();
            currentConfig.addProperty("name", module.getName());
            JsonArray pairs = new JsonArray();
            for (DynamicValue<?> dynamicValue : module.config.getConfig()){
                JsonObject god = new JsonObject();
                god.addProperty("key", dynamicValue.getKey());
                god.addProperty("value", dynamicValue.getValue().toString());
                pairs.add(god);
            }
            currentConfig.add("pairs", pairs);
            config.add(currentConfig);
        }
        base.add("enabled", enabled);
        base.add("config", config);
        try {
            FileUtils.writeStringToFile(MODULE_CONFIG_FILE, TOP + base, Charsets.UTF_8, false);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void loadState(){
        if(loaded) return;
        loaded = true;
        try {
            if (!MODULE_CONFIG_FILE.isFile()) MODULE_CONFIG_FILE.delete();
            if(!MODULE_CONFIG_FILE.exists()) return;
            String jsonString = FileUtils.readFileToString(MODULE_CONFIG_FILE, Charsets.UTF_8);
            JsonObject config = new JsonParser().parse(jsonString).getAsJsonObject();
            if(config.has("config") && config.get("config").isJsonArray()){
                JsonArray configArray = config.get("config").getAsJsonArray();
                for (JsonElement jsonElement : configArray){
                    if(jsonElement.isJsonObject()){
                        JsonObject obj = jsonElement.getAsJsonObject();
                        String name = obj.get("name").getAsString();
                        Module module = ModuleRegistry.getByName(name);
                        if(module == null) continue;
                        if(obj.has("pairs") && obj.get("pairs").isJsonArray()){
                            JsonArray pairs = obj.get("pairs").getAsJsonArray();
                            for(JsonElement pair : pairs){
                                JsonObject jo = pair.getAsJsonObject();
                                String key = jo.get("key").getAsString();
                                String value = jo.get("value").getAsString();
                                DynamicValue<?> dy = module.config.get(key);
                                if(dy != null){
                                    Object newValue = TypeConverter.convert(value, dy.getType());
                                    if(newValue != null) dy.setValue(newValue);
                                }
                            }
                        }
                    }
                }
            }

            if(config.has("enabled") && config.get("enabled").isJsonArray()){
                for(JsonElement jsonElement : config.get("enabled").getAsJsonArray()){
                    String name = jsonElement.getAsString();
                    Module module = ModuleRegistry.getByName(name);
                    if(module != null) toEnable.add(module);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            KeybindManager.reload();
        }
    }

    public static void enableModules(){
        if(enabled) return;
        enabled = true;
        for(Module module : toEnable){
            module.setEnabled(true);
        }
    }
}
