package me.lolkas.client.funStuff.modules.config;

//Skidded from atomic https://github.com/cornos/Atomic

import java.util.ArrayList;
import java.util.List;

public class ModuleConfig {
    final List<DynamicValue<?>> config = new ArrayList<>();

    public <T> DynamicValue<T> create(String key, T value){
        DynamicValue<T> dv = new DynamicValue<>(key, value);
        config.add(dv);
        return dv;
    }

    public BooleanValue create(String key, boolean inital){
        BooleanValue b = new BooleanValue(key, inital);
        config.add(b);
        return b;
    }

    public SliderValue create(String key, double value, double min, double max, int prc){
        SliderValue s = new SliderValue(key, value, min, max, prc);
        config.add(s);
        return s;
    }

    public MultiValue create(String key, String value, String... possible){
        MultiValue m = new MultiValue(key, value, possible);
        config.add(m);
        return m;
    }

    public DynamicValue<?> get(String key){
        for (DynamicValue<?> dy : config){
            if(dy.getKey().equalsIgnoreCase(key)) return dy;
        }
        return null;
    }

    public List<DynamicValue<?>> getConfig() {return config;}
}
