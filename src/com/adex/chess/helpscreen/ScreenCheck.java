package com.adex.chess.helpscreen;

import com.adex.chess.visual.Images;
import com.adex.chess.visual.Panel;

import java.awt.*;

public class ScreenCheck extends HelpScreen {

    public ScreenCheck() {
        super(new String[]{"Your king is in check if an opposing unit can eat it."
                , "If your king is at check you have 3 options you can do:"
                , "1) Capture the unit that checks your king. You can't"
                , "do this if more than one unit does check your king."
                , "2) Move an unit between your king and the unit(s)"
                , "checking it."
                , "3) Move your king to another tile."
                , "If you can't do any of these you will lose the game."});
    }

    @Override
    public void draw(Graphics g, Panel panel) {
        super.draw(g, panel);

        Image canMove = panel.getImage("yellow");
        Image checking = panel.getImage("dark red");
        Image danger = panel.getImage("light red");

        Image kingW = Images.getImage("king", true);
        Image rookB = Images.getImage("rook", false);

        g.drawImage(checking, 600, 500, panel);
        g.drawImage(danger, 200, 500, panel);
        g.drawImage(danger, 300, 500, panel);
        g.drawImage(danger, 400, 500, panel);

        g.drawImage(canMove, 200, 400, panel);
        g.drawImage(canMove, 300, 400, panel);
        g.drawImage(canMove, 400, 400, panel);
        g.drawImage(canMove, 200, 600, panel);
        g.drawImage(canMove, 300, 600, panel);
        g.drawImage(canMove, 400, 600, panel);

        g.drawImage(kingW, 300, 500, panel);
        g.drawImage(rookB, 600, 500, panel);
    }
}
