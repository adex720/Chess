package com.adex.chess.settings;

import com.adex.chess.visual.Panel;

public class VisualSetting {

    private final String name;

    private final int minVal;
    private final int maxVal;
    private int value; // For boolean: use 0 or 1

    private final boolean showValue;
    private final boolean hasChangeArrows;

    private final VisualSetting showOnlyWhen1;


    public VisualSetting(String name, int minVal, int maxVal, int value, boolean showValue, boolean hasChangeArrows) {
        this.name = name;
        this.minVal = minVal;
        this.maxVal = maxVal;
        this.value = value;
        this.showValue = showValue;
        this.hasChangeArrows = hasChangeArrows;
        showOnlyWhen1 = ALWAYS_TRUE;
    }

    public VisualSetting(String name, int minVal, int maxVal, int value, boolean showValue, boolean hasChangeArrows, VisualSetting showOnlyWhen1) {
        this.name = name;
        this.minVal = minVal;
        this.maxVal = maxVal;
        this.value = value;
        this.showValue = showValue;
        this.hasChangeArrows = hasChangeArrows;
        this.showOnlyWhen1 = showOnlyWhen1;
    }

    public void valueChanged(int to) {
        if (to >= minVal && to <= maxVal) value = to;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public boolean show() {
        return showOnlyWhen1.getValue() == 1;
    }

    public boolean showValue() {
        return showValue;
    }

    public boolean hasChangeArrows() {
        return hasChangeArrows;
    }

    /**
     * Adds 1 to the value.
     */
    public void add() {
        value++;
        if (value > maxVal) value = minVal;
    }

    public void reduce() {
        value--;
        if (value < minVal) value = maxVal;
    }

    public void pressed(Panel panel) { }

    public static final VisualSetting ALWAYS_TRUE = new VisualSetting("", 1, 1, 1, false, false);
}
