package com.adex.chess.unit;

import com.adex.chess.visual.Images;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public enum Type {

    ROOK("", Images.getImage("rook", true), Images.getImage("rook", false)),
    PAWN("", Images.getImage("pawn", true), Images.getImage("pawn", false)),
    BISHOP("", Images.getImage("bishop", true), Images.getImage("bishop", false)),
    KNIGHT("", Images.getImage("knight", true), Images.getImage("knight", false)),
    QUEEN("", Images.getImage("queen", true), Images.getImage("queen", false)),
    KING("", Images.getImage("king", true), Images.getImage("king", false));

    private final String name;
    private final Image white;
    private final Image black;

    Type(String name, Image white, Image black) {
        this.name = name;
        this.white = white;
        this.black = black;
    }

    public static Type getRandom() {
        switch (ThreadLocalRandom.current().nextInt(4)) {
            case 0 -> {
                return ROOK;
            }
            case 1 -> {
                return BISHOP;
            }
            case 2 -> {
                return KING;
            }
            case 3 -> {
                return QUEEN;
            }
        }

        return PAWN;
    }

    public static Type getWeightedRandom() {
        switch (ThreadLocalRandom.current().nextInt(6)) {
            case 0, 1 -> {
                return ROOK;
            }
            case 2, 3  -> {
                return BISHOP;
            }
            case 4 -> {
                return KING;
            }
            case 5 -> {
                return QUEEN;
            }
        }

        return PAWN;
    }

    public String getName() {
        return name;
    }

    public Image getImage(boolean white) {
        if (white) return this.white;
        return black;
    }

    public Image getWhite() {
        return white;
    }

    public Image getBlack() {
        return black;
    }
}
