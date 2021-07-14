package com.lolkas.client.funStuff;

import com.lolkas.client.funStuff.esp.ChestESP;
import com.lolkas.client.funStuff.esp.ESP;
import com.lolkas.client.funStuff.tracers.Tracers;

import java.util.ArrayList;
import java.util.List;

public class ModuleRegistry {
    static List<Module> modules = new ArrayList<>();

    static {
        modules.add(new Tracers());
        modules.add(new ESP());
        modules.add(new ChestESP());
    }

    public static List<Module> getModules(){return modules;}
    public static Module getByName(String n) {
        for (Module module : getModules()) {
            if (module.getName().equalsIgnoreCase(n)) return module;
        }
        return null;
    }
}
