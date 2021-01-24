package com.adex.chess.helpscreen;

import com.adex.chess.visual.Images;
import com.adex.chess.visual.Panel;

import java.awt.*;

public class ScreenQueen extends HelpScreen {

    public ScreenQueen() {
        super(new String[]{"This is queen. It can move unlimited times"
                , "in every direction but can't change"
                , "the direction on the way. Like rook and bishop,"
                , "it can't move trough other units."});
    }

    @Override
    public void draw(Graphics g, Panel panel) {
        super.draw(g, panel);

        Image canMove = panel.getImage("yellow");

        Image queenW = Images.getImage("queen", true);
        Image queenB = Images.getImage("queen", false);
        Image rookW = Images.getImage("rook", true);
        Image rookB = Images.getImage("rook", false);

        g.drawImage(canMove, 100, 700, panel);
        g.drawImage(canMove, 200, 700, panel);
        g.drawImage(canMove, 300, 700, panel);
        g.drawImage(canMove, 500, 700, panel);
        g.drawImage(canMove, 400, 300, panel);
        g.drawImage(canMove, 400, 400, panel);
        g.drawImage(canMove, 400, 500, panel);
        g.drawImage(canMove, 400, 600, panel);
        g.drawImage(canMove, 300, 600, panel);
        g.drawImage(canMove, 200, 500, panel);
        g.drawImage(canMove, 100, 400, panel);
        g.drawImage(canMove, 0, 300, panel);
        g.drawImage(canMove, 500, 600, panel);
        g.drawImage(canMove, 600, 500, panel);
        g.drawImage(canMove, 700, 400, panel);

        g.drawImage(queenW, 400, 700, panel);
        g.drawImage(queenB, 400, 300, panel);
        g.drawImage(rookB, 100, 700, panel);
        g.drawImage(rookW, 600, 700, panel);
    }
}
