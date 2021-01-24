package com.adex.chess.helpscreen;

import com.adex.chess.visual.Images;
import com.adex.chess.visual.Panel;

import java.awt.*;

public class ScreenOverall extends HelpScreen {

    public ScreenOverall() {
        super(new String[]{"To win a game you have to capture your opponent's"
                , "king. To capture an unit you have to move your unit"
                , "on the tile of the unit you want to capture is."
                , "Try eating as many of your opponents units as"
                , "possible and don't let your opponent eat your"
                , "important units."});
    }

    @Override
    public void draw(Graphics g, Panel panel) {
        super.draw(g, panel);

        Image king = Images.getImage("king", true);

        g.drawImage(king, 400, 600, panel);

    }
}
