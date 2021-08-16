package me.lolkas.client.helper.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.lolkas.client.funStuff.modules.ModuleRegistry;
import me.lolkas.client.gui.ClickGui.ClickScreen;
import me.lolkas.client.gui.ClickGui.ConfigGui;
import me.lolkas.client.gui.ClickGui.Draggable;
import net.minecraft.client.MinecraftClient;
import org.apache.commons.compress.utils.Charsets;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//i actually write this and dint skid

public class ConfigScreen {
    static final MinecraftClient client = MinecraftClient.getInstance();
    static final File SCREEN_CONFIG;
    static boolean loaded = false;
    static final String TOP = """
            //it saves click gui
            //when you dont skid :flushed:
            //amogus file lmao
            """;
    static final List<JsonObject> draggables = new ArrayList<>();

    static {
        SCREEN_CONFIG = new File(client.runDirectory.getAbsolutePath() + "/lolclient/screen.amogus");
    }

    public static JsonObject get(String name){
        for (JsonObject jsonObj : draggables){
            if(jsonObj.get("name").getAsString().equals(name)) return jsonObj;
        }
        return null;
    }

    public static void saveState(){
        if(!loaded) return;
        List<Draggable> draggableList = ClickScreen.INSTANCE.containers;
        JsonObject base = new JsonObject();
        JsonArray array = new JsonArray();
        JsonObject config = new JsonObject();
        for(Draggable drag : draggableList){
            JsonObject dragObj = new JsonObject();
            dragObj.addProperty("name", drag.title);
            dragObj.addProperty("x", drag.posX);
            dragObj.addProperty("y", drag.posY);
            dragObj.addProperty("expanded", drag.expanded);
            array.add(dragObj);
        }
        if(ClickScreen.INSTANCE.config != null){
            ConfigGui configGui = ClickScreen.INSTANCE.config;
            config.addProperty("module", configGui.module.getName());
            config.addProperty("x", configGui.x);
            config.addProperty("y", configGui.y);
        }
        base.add("array", array);
        base.add("config", config);
        try {
            FileUtils.writeStringToFile(SCREEN_CONFIG, TOP + base, Charsets.UTF_8, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadState(){
        loaded = true;
        try {
            if (!SCREEN_CONFIG.isFile()) SCREEN_CONFIG.delete();
            if(!SCREEN_CONFIG.exists()) return;
            String jsonString = FileUtils.readFileToString(SCREEN_CONFIG, Charsets.UTF_8);
            JsonObject arrayObj = new JsonParser().parse(jsonString).getAsJsonObject();
            if(arrayObj.has("array") && arrayObj.get("array").isJsonArray()){
                JsonArray drag = arrayObj.get("array").getAsJsonArray();
                draggables.clear();
                for(JsonElement jsonElement : drag){
                    draggables.add(jsonElement.getAsJsonObject());
                }
            }
            if(arrayObj.has("config") && arrayObj.get("config").isJsonObject()){
                JsonObject config = arrayObj.get("config").getAsJsonObject();
                if(config.get("module") != null && ClickScreen.INSTANCE != null){
                    ConfigGui configGui = new ConfigGui(ModuleRegistry.getByName(config.get("module").getAsString()));
                    configGui.x = config.get("x").getAsInt();
                    configGui.y = config.get("y").getAsInt();
                    ClickScreen.INSTANCE.config = configGui;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
