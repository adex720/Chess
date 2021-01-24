package com.adex.chess.helpscreen;

import com.adex.chess.visual.Images;
import com.adex.chess.visual.Panel;

import java.awt.*;

public class ScreenKnight extends HelpScreen {

    public ScreenKnight() {
        super(new String[]{"This is knight. It will always move 1 or 2 steps"
                , "horizontally and then the other amount vertically."
                , "Unlike other units it can move over other units."
                , "(But doesn't eat them)"});
    }

    @Override
    public void draw(Graphics g, Panel panel) {
        super.draw(g, panel);

        Image canMove = panel.getImage("yellow");

        Image knightW = Images.getImage("knight", true);
        Image knightB = Images.getImage("knight", false);

        g.drawImage(canMove, 300, 500, panel);
        g.drawImage(canMove, 300, 300, panel);
        g.drawImage(canMove, 400, 200, panel);
        g.drawImage(canMove, 600, 200, panel);
        g.drawImage(canMove, 700, 300, panel);
        g.drawImage(canMove, 700, 500, panel);
        g.drawImage(canMove, 600, 600, panel);
        g.drawImage(canMove, 400, 600, panel);

        g.drawImage(knightW, 500, 400, panel);
        g.drawImage(knightB, 0, 500, panel);
    }
}
