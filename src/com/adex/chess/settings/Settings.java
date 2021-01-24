package com.adex.chess.settings;

public class Settings {

    private int undos;
    private int time;

    private Settings() {
        undos = 3;
        time = 30;
    }

    public static Settings getDefault() {
        return new Settings();
    }

    public int getUndos() {
        return undos;
    }

    public Settings setUndos(int undos) {
        this.undos = undos;
        return this;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

}
