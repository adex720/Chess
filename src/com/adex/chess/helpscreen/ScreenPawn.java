package com.adex.chess.helpscreen;

import com.adex.chess.visual.Images;
import com.adex.chess.visual.Panel;

import java.awt.*;

public class ScreenPawn extends HelpScreen {

    public ScreenPawn() {
        super(new String[]{"This is pawn. It can move one steps forward"
                , "and can eat only diagonally. If the pawn"
                , "hasn't moved yet it can move 2 steps forward"
                , "but be careful because an opposing pawn"
                , "can eat it on its way by moving where it remained."});
    }

    @Override
    public void draw(Graphics g, Panel panel) {
        super.draw(g, panel);

        Image canMove = panel.getImage("yellow");

        Image pawnW = Images.getImage("pawn", true);
        Image pawnB = Images.getImage("pawn", false);

        g.drawImage(canMove, 200, 500, panel);
        g.drawImage(canMove, 600, 500, panel);
        g.drawImage(canMove, 600, 400, panel);

        g.drawImage(pawnW, 600, 600, panel);
        g.drawImage(pawnW, 300, 600, panel);
        g.drawImage(pawnB, 200, 500, panel);
        g.drawImage(pawnB, 300, 500, panel);
    }
}
