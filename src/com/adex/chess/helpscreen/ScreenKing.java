package com.adex.chess.helpscreen;

import com.adex.chess.visual.Images;
import com.adex.chess.visual.Panel;

import java.awt.*;

public class ScreenKing extends HelpScreen {

    public ScreenKing() {
        super(new String[]{"This is king. It can move in every direction"
                , "but only one step. It can't move to a tile"
                , "where it can be eaten."});
    }

    @Override
    public void draw(Graphics g, Panel panel) {
        super.draw(g, panel);

        Image canMove = panel.getImage("yellow");
        Image danger = panel.getImage("light red");

        Image kingW = Images.getImage("king", true);
        Image kingB = Images.getImage("king", false);

        Image bishopB = Images.getImage("bishop", false);

        g.drawImage(canMove, 100, 500, panel);
        g.drawImage(canMove, 200, 500, panel);
        g.drawImage(canMove, 300, 500, panel);
        g.drawImage(canMove, 300, 700, panel);
        g.drawImage(canMove, 100, 700, panel);
        g.drawImage(canMove, 100, 600, panel);
        g.drawImage(danger, 300, 600, panel);
        g.drawImage(danger, 200, 700, panel);

        g.drawImage(kingW, 200, 600, panel);
        g.drawImage(kingB, 700, 500, panel);
        g.drawImage(bishopB, 600, 300, panel);
    }
}
