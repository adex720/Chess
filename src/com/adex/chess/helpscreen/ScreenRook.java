package com.adex.chess.helpscreen;

import com.adex.chess.visual.Images;
import com.adex.chess.visual.Panel;

import java.awt.*;

public class ScreenRook extends HelpScreen {

    public ScreenRook() {
        super(new String[]{"This is rook. It can move unlimited times"
                , "horizontally or vertically but not diagonally."
                , "It can't move through another units"
                , "or go outside of the board."});
    }

    @Override
    public void draw(Graphics g, Panel panel) {
        super.draw(g, panel);

        Image canMove = panel.getImage("yellow");

        Image rookW = Images.getImage("rook", true);
        Image rookB = Images.getImage("rook", false);

        g.drawImage(canMove, 0, 500, panel);
        g.drawImage(canMove, 100, 500, panel);
        g.drawImage(canMove, 200, 500, panel);
        g.drawImage(canMove, 300, 500, panel);
        g.drawImage(canMove, 400, 500, panel);
        g.drawImage(canMove, 500, 500, panel);
        g.drawImage(canMove, 600, 500, panel);
        g.drawImage(canMove, 700, 500, panel);

        g.drawImage(canMove, 600, 200, panel);
        g.drawImage(canMove, 600, 300, panel);
        g.drawImage(canMove, 600, 400, panel);
        g.drawImage(canMove, 600, 500, panel);
        g.drawImage(canMove, 600, 600, panel);
        g.drawImage(canMove, 600, 700, panel);

        g.drawImage(rookW, 600, 500, panel);
        g.drawImage(rookB, 600, 200, panel);
    }
}
