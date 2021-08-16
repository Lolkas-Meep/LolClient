package me.lolkas.client.funStuff.modules.config;


//Skidded from atomic https://github.com/cornos/Atomic

import net.minecraft.util.math.MathHelper;

public class SliderValue extends DynamicValue<Double>{
    final int prec;
    double min;
    double max;

    public SliderValue(String key, double value, double min, double max, int prec){
        super(key, value);
        this.prec = MathHelper.clamp(prec, 0 ,10);
        this.min = min;
        this.max = max;
    }

    @Override
    public void setValue(Object value) {
        if (!(value instanceof Double)) return;
        this.value = MathHelper.clamp((double) value, min, max);
        onValueChanged();
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public int getPrec() {
        return prec;
    }
}
