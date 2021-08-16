package me.lolkas.client.helper.config;

public class ConfigManager {
    public static void loadConfigs(){
        ConfigModule.loadState();
        ConfigScreen.loadState();
        System.out.println("Loading Configs");
    }

    public static void saveConfigs(){
        ConfigModule.saveState();
        System.out.println("Saving Configs");
    }
}
