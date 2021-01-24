package com.adex.chess.helpscreen;

import com.adex.chess.visual.Images;
import com.adex.chess.visual.Panel;

import java.awt.*;

public class ScreenBishop extends HelpScreen {

    public ScreenBishop() {
        super(new String[]{"This is bishop. It can move unlimited times diagonally"
                , "but not horizontally ot vertically. Like rook,"
                , "it can't move through another units"
                , "or go outside of the board."});
    }

    @Override
    public void draw(Graphics g, Panel panel) {
        super.draw(g, panel);

        Image canMove = panel.getImage("yellow");

        Image bishopW = Images.getImage("bishop", true);
        Image bishopB = Images.getImage("bishop", false);

        g.drawImage(canMove, 0, 300, panel);
        g.drawImage(canMove, 100, 400, panel);
        g.drawImage(canMove, 200, 500, panel);
        g.drawImage(canMove, 400, 500, panel);
        g.drawImage(canMove, 500, 400, panel);
        g.drawImage(canMove, 600, 300, panel);
        g.drawImage(canMove, 700, 200, panel);
        g.drawImage(canMove, 800, 100, panel);
        g.drawImage(canMove, 200, 700, panel);
        g.drawImage(canMove, 400, 700, panel);

        g.drawImage(bishopW, 300, 600, panel);
        g.drawImage(bishopB, 200, 300, panel);

    }
}
