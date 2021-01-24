package com.adex.chess.helpscreen;

import com.adex.chess.visual.Images;
import com.adex.chess.visual.Panel;

import java.awt.*;

public class ScreenCastling extends HelpScreen {

    public ScreenCastling() {
        super(new String[]{"Castling is action where your king defends itself by"
                , "switching places with a rook, which you can only do if"
                , "you haven't moved you king or the rook you are doing it"
                , "with and all the tiles between them are empty."
                , "To perform castling simply select your king and"
                , "try to move it into the tower you want to perform"
                , "the castling it with."});
    }

    @Override
    public void draw(Graphics g, Panel panel) {
        super.draw(g, panel);

        Image canMove = panel.getImage("yellow");

        Image kingW = Images.getImage("king", true);

        Image rookW = Images.getImage("rook", true);

        g.drawImage(canMove, 200, 700, panel);
        g.drawImage(canMove, 200, 600, panel);
        g.drawImage(canMove, 300, 600, panel);
        g.drawImage(canMove, 400, 600, panel);
        g.drawImage(canMove, 400, 700, panel);
        g.drawImage(canMove, 0, 700, panel);

        g.drawImage(kingW, 300, 700, panel);
        g.drawImage(rookW, 0, 700, panel);

        g.drawString("The back-button is still down there.", 10, 595);
        Panel.drawArrow(g, 30, 650, 40, false);
    }
}
