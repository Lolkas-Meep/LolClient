package me.lolkas.client.funStuff.modules;

import me.lolkas.client.funStuff.modules.combat.AutoClicker;
import me.lolkas.client.funStuff.modules.movement.NoFall;
import me.lolkas.client.funStuff.modules.movement.Speed;
import me.lolkas.client.funStuff.modules.render.*;
import me.lolkas.client.funStuff.modules.movement.Flight;
import me.lolkas.client.funStuff.modules.world.Freecam;
import me.lolkas.client.funStuff.modules.world.Xray;

import java.util.ArrayList;
import java.util.List;

public class ModuleRegistry {
    static List<Module> modules = new ArrayList<>();

    static {
        modules.add(new Tracers());
        modules.add(new ESP());
        modules.add(new AutoClicker());
        modules.add(new ChestESP());
        modules.add(new Flight());
        modules.add(new Hud());
        modules.add(new NoFall());
        modules.add(new NoRenderWithers());
        modules.add(new Speed());
        modules.add(new Fullbright());
        modules.add(new Freecam());
        modules.add(new Xray());
    }

    public static List<Module> getModules(){return modules;}
    public static Module getByName(String n) {
        for (Module module : getModules()) {
            if (module.getName().equalsIgnoreCase(n)) return module;
        }
        return null;
    }
    public static Module getByClass(Class<? extends Module> clazz) {
        for (Module module : getModules()) {
            if (module.getClass() == clazz) return module;
        }
        return null;
    }
}
