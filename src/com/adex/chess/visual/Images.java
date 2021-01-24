package com.adex.chess.visual;

import javax.swing.*;
import java.awt.*;

public class Images {

    private static final Image whitePawn;
    private static final Image whiteRook;
    private static final Image whiteBishop;
    private static final Image whiteKnight;
    private static final Image whiteQueen;
    private static final Image whiteKing;

    private static final Image blackPawn;
    private static final Image blackRook;
    private static final Image blackBishop;
    private static final Image blackKnight;
    private static final Image blackQueen;
    private static final Image blackKing;

    static {
        ImageIcon ii1 = new ImageIcon("src/resources/pawn1.png");
        whitePawn = ii1.getImage();
        ImageIcon ii2 = new ImageIcon("src/resources/rook1.png");
        whiteRook = ii2.getImage();
        ImageIcon ii3 = new ImageIcon("src/resources/bishop1.png");
        whiteBishop = ii3.getImage();
        ImageIcon ii4 = new ImageIcon("src/resources/knight1.png");
        whiteKnight = ii4.getImage();
        ImageIcon ii5 = new ImageIcon("src/resources/queen1.png");
        whiteQueen = ii5.getImage();
        ImageIcon ii6 = new ImageIcon("src/resources/king1.png");
        whiteKing = ii6.getImage();
        ImageIcon ii7 = new ImageIcon("src/resources/pawn2.png");
        blackPawn = ii7.getImage();
        ImageIcon ii8 = new ImageIcon("src/resources/rook2.png");
        blackRook = ii8.getImage();
        ImageIcon ii9 = new ImageIcon("src/resources/bishop2.png");
        blackBishop = ii9.getImage();
        ImageIcon ii10 = new ImageIcon("src/resources/knight2.png");
        blackKnight = ii10.getImage();
        ImageIcon ii11 = new ImageIcon("src/resources/queen2.png");
        blackQueen = ii11.getImage();
        ImageIcon ii12 = new ImageIcon("src/resources/king2.png");
        blackKing = ii12.getImage();
    }

    public static Image getImage(String name, boolean white) {

        if (white) {
            if (name.equalsIgnoreCase("pawn")) return whitePawn;
            if (name.equalsIgnoreCase("rook")) return whiteRook;
            if (name.equalsIgnoreCase("bishop")) return whiteBishop;
            if (name.equalsIgnoreCase("knight")) return whiteKnight;
            if (name.equalsIgnoreCase("queen")) return whiteQueen;
            if (name.equalsIgnoreCase("king")) return whiteKing;

        } else {
            if (name.equalsIgnoreCase("pawn")) return blackPawn;
            if (name.equalsIgnoreCase("rook")) return blackRook;
            if (name.equalsIgnoreCase("bishop")) return blackBishop;
            if (name.equalsIgnoreCase("knight")) return blackKnight;
            if (name.equalsIgnoreCase("queen")) return blackQueen;
            if (name.equalsIgnoreCase("king")) return blackKing;
        }
        return null;
    }
}
