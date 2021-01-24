package com.adex.chess.helpscreen;

import com.adex.chess.game.Position;
import com.adex.chess.visual.Panel;

import java.awt.*;
import java.util.ArrayList;

public abstract class HelpScreen {

    protected final String[] text; //Each row to as its own String.
    private final int id;

    private static final int startId = 1;
    private static int lastId = 0;

    private static final ArrayList<HelpScreen> SCREENS = new ArrayList<>();

    public HelpScreen(String[] text) {
        this.text = text;
        lastId++;
        id = lastId;
        SCREENS.add(this);
    }

    public void draw(Graphics g, Panel panel) {
        Panel.drawSidewaysArrow(g, 20, 20, 60, true);
        if (id != startId) Panel.drawSidewaysArrow(g, 20, 720, 60, true);
        if (id !=lastId) Panel.drawSidewaysArrow(g, 720, 720, 60, false);

        int x = 32;
        int y = 120;

        g.setColor(Color.white);
        g.setFont(getDefaultFont());
        for (String text: text) {
            g.drawString(text, x, y);
            y += 30;
        }
    }

    public Font getDefaultFont() {
        return new Font("Arial", Font.BOLD, 30);
    }

    public static HelpScreen getScreen(int id) {
        for (HelpScreen screen : SCREENS) {
            if (screen.id == id) return screen;
        }
        return null;
    }

    public static int getTotalScreens(){
        return SCREENS.size();
    }

    public int getId() {
        return id;
    }

    /**
     * 0=none
     * 1=exit
     * 2=previous
     * 3=next
     * */
    public static int getButtonId(Position position){
        if (position.getX() >= 20 && position.getX() <= 80 && position.getY() >= 20 && position.getY() <= 80) return 1;
        if (position.getX() >= 20 && position.getX() <= 80 && position.getY() >= 720 && position.getY() <= 780) return 2;
        if (position.getX() >= 720 && position.getX() <= 780 && position.getY() >= 720 && position.getY() <= 780) return 3;

        return 0;
    }

}
