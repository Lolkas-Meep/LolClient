package me.lolkas.client.funStuff.modules.config.keybind;

import me.lolkas.client.funStuff.modules.Module;
import me.lolkas.client.funStuff.modules.ModuleRegistry;

import java.util.HashMap;
import java.util.Map;

public class KeybindManager {
    public static final Map<Module, Keybinds> keyMap = new HashMap<>();

    public static void start(){
        for (Module module : ModuleRegistry.getModules()){
            if(!module.config.get("Keybind").getValue().equals(-1)){
                keyMap.put(module, new Keybinds(Integer.parseInt(module.config.get("Keybind").getValue() + "")));
            }
        }
    }

    public static void update(){
        for (Module module : keyMap.keySet().toArray(new Module[0])){
            Keybinds kb = keyMap.get(module);
            if(kb.isPressed()){
                module.toggle();
            }
        }
    }

    public static void reload(){
        keyMap.clear();
        start();
    }
}
