package me.lolkas.client.funStuff.modules;

public enum  ModuleType {
    MOVEMENT("Movement"), RENDER("Render"), COMBAT("Combat"), HIDDEN(""), WORLD("World");
    final String name;
    ModuleType(String n){
        this.name = n;
    }
    public String getName(){
        return name;
    }
}
