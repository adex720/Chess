package com.adex.chess.helpscreen;

import com.adex.chess.visual.Images;
import com.adex.chess.visual.Panel;

import java.awt.*;

public class ScreenCastling2 extends HelpScreen {

    public ScreenCastling2() {
        super(new String[]{"However you can't perform castling if your king"
                , "is in check or moves/remains on a tile it is in check."
                , "You can perform castling with both of your towers."
                , "(but not in the same game because the must not "
                , "have moved before castling.)"});
    }

    @Override
    public void draw(Graphics g, Panel panel) {
        super.draw(g, panel);

        Image kingW = Images.getImage("king", true);

        Image rookW = Images.getImage("rook", true);

        g.drawImage(kingW, 100, 700, panel);
        g.drawImage(rookW, 200, 700, panel);
    }
}
