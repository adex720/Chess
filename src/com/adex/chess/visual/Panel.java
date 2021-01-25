package com.adex.chess.visual;

import com.adex.chess.Chess;
import com.adex.chess.game.Game;
import com.adex.chess.game.Position;
import com.adex.chess.helpscreen.*;
import com.adex.chess.settings.Settings;
import com.adex.chess.settings.VisualSetting;
import com.adex.chess.settings.VisualSettingExit;
import com.adex.chess.unit.Type;
import com.adex.chess.unit.Unit;
import com.adex.chess.unit.movement.BackgroundUnit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.Timer;

public class Panel extends JPanel {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;
    public static final int SQUARE_SIZE = 100;

    private final Settings settings;

    public Game game;
    public boolean gameOn;
    private int screenId = 0;

    public final ArrayList<Button> buttons;
    public final ArrayList<VisualSetting> settingButtons;
    private VisualSetting undos;
    private VisualSetting timer;
    private VisualSetting time;
    private VisualSetting exit;


    private Image light;
    private Image dark;
    private Image selected;

    private Image danger;
    private Image danger_dark;
    private Image danger_light;


    private int selectionX = 0;
    private int selectionY = 0;
    private int selectionSize = 0;


    // for menus
    private final int startX = 280;
    private final int startY = 280;

    private final int width = 240;
    private final int height = 60;
    private final int margin = 10;

    private final int valueWidth = 60;
    private final int valueHeight = 60;
    private final int valueMargin = 15;

    private final ArrayList<BackgroundUnit> backGroundUnits;
    private final Timer moveTimer;

    private int forDebug = 0;


    public Panel() {
        settings = Settings.getDefault();

        buttons = new ArrayList<>();
        settingButtons = new ArrayList<>();
        backGroundUnits = new ArrayList<>();

        moveTimer = new Timer();

        loadImages();
        loadSettings();
        loadHelpScreens();
        initBoard();
        initTimer();

        addBackgroundUnits(10);

    }

    /**
     * Also leaves from settings.
     */
    public void saveSettings() {
        settings.setUndos(undos.getValue()).setTime(time.getValue());
        settings.setTimer(timer.getValue() == 1);
        screenId = 0;
        repaint();
    }

    private void startGame() {
        gameOn = true;
        screenId = 1;
    }

    private void initBoard() {

        setBackground(Color.WHITE);
        addMouseListener(new MouseListener());
        setFocusable(true);

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    private void loadImages() {

        ImageIcon iia = new ImageIcon("src/resources/dark.png");
        light = iia.getImage();
        ImageIcon iib = new ImageIcon("src/resources/light.png");
        dark = iib.getImage();
        ImageIcon iic = new ImageIcon("src/resources/selected.png");
        selected = iic.getImage();

        ImageIcon iid = new ImageIcon("src/resources/dark_red.png");
        danger = iid.getImage();
        ImageIcon iie = new ImageIcon("src/resources/light_red.png");
        danger_light = iie.getImage();
        ImageIcon iif = new ImageIcon("src/resources/middle_red.png");
        danger_dark = iif.getImage();
    }

    private void loadSettings() {
        undos = new VisualSetting("Undos", 0, 20, 3, true, true);
        timer = new VisualSetting("Use timer", 0, 1, 0, false, false);
        time = new VisualSetting("Time (Minutes)", 1, 60, 30, true, true, timer);
        exit = new VisualSettingExit();

        settingButtons.add(undos);
        settingButtons.add(timer);
        settingButtons.add(time);
        settingButtons.add(exit);
    }

    private void loadHelpScreens() { // Order do matter
        new ScreenOverall();
        new ScreenCheck();
        new ScreenPawn();
        new ScreenRook();
        new ScreenBishop();
        new ScreenKnight();
        new ScreenQueen();
        new ScreenKing();
        new ScreenCastling();
        new ScreenCastling2();
    }

    private void initTimer() {
        moveTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (BackgroundUnit unit : backGroundUnits) {
                    unit.move();
                    repaint();
                }
            }
        }, 0, 15);
    }

    public void addBackgroundUnits(int amount) {
        while (amount > 0) {
            backGroundUnits.add(new BackgroundUnit(new Random()));
            amount--;
        }
    }

    public Image getImage(String name) {
        if (name.equalsIgnoreCase("yellow")) return selected;
        if (name.equalsIgnoreCase("light brown")) return light;
        if (name.equalsIgnoreCase("dark brown")) return dark;
        if (name.equalsIgnoreCase("light red")) return danger_light;
        if (name.equalsIgnoreCase("red")) return danger_dark;
        if (name.equalsIgnoreCase("dark red")) return danger;

        return null;
    }

    //Drawing
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {

        drawBoard(g);

        if (backGroundUnits.size() > 0) {
            drawBackgroundUnits(g);
        }

        switch (screenId) {
            //Main settings
            case 0 -> drawMenu(g);
            //Game
            case 1 -> drawGame(g);
            //Settings
            case 2 -> drawSettings(g);
            default -> drawHelp(g, screenId - 2);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawBoard(Graphics g) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (x % 2 == y % 2) {
                    g.drawImage(light, SQUARE_SIZE * x, SQUARE_SIZE * y, this);
                } else {
                    g.drawImage(dark, SQUARE_SIZE * x, SQUARE_SIZE * y, this);
                }
            }
        }
    }

    private void drawGame(Graphics g) {
        if (game.gameOver) {
            drawGameOver(g, game.isWhiteWinner);
            return;
        }

        //Selected
        Position selectedPosition = game.getSelectedScreenPosition();
        if (selectedPosition != null)
            g.drawImage(selected, selectedPosition.getX(), selectedPosition.getY(), this);

        //Where king can't move
        if (game.getSelected() != null) {
            if (game.getSelected().getType() == Type.KING) {
                Unit king = game.getSelected();
                int kingX = king.getPosition().getX();
                int kingY = king.getPosition().getY();
                int[] x = {-1, 0, 1, -1, 0, 1, -1, 0, 1};
                int[] y = {1, 1, 1, 0, 0, 0, -1, -1, -1};

                for (Unit unit : game.getUnits(!game.isWhitesTurn())) {
                    for (int i = 0; i < 9; i++) {
                        king.setPosition(new Position(kingX + x[i], kingY + y[i]));
                        if (unit.canMove(unit.getPosition(), new Position(kingX + x[i], kingY + y[i]), game)) {
                            if ((kingX + x[i]) % 2 != (kingY + y[i]) % 2) {
                                g.drawImage(danger_light, SQUARE_SIZE * (kingX + x[i]), SQUARE_SIZE * (kingY + y[i]), this);
                            } else {
                                g.drawImage(danger_dark, SQUARE_SIZE * (kingX + x[i]), SQUARE_SIZE * (kingY + y[i]), this);
                            }
                        }
                    }
                }

                king.setPosition(new Position(kingX, kingY));
            }

        }

        //King in danger
        Unit king = game.getKing(game.isWhitesTurn());
        int kingX = king.getPosition().getX();
        int kingY = king.getPosition().getY();

        for (Unit unit : game.getUnits(!game.isWhitesTurn())) {
            if (unit.canMove(unit.getPosition(), new Position(kingX, kingY), game)) {
                g.drawImage(danger, SQUARE_SIZE * unit.getPosition().getX(), SQUARE_SIZE * unit.getPosition().getY(), this);

            }
        }

        //Units
        for (Unit unit : game.getUnits()) {
            g.drawImage(unit.getType().getImage(unit.isWhite()), SQUARE_SIZE * unit.getPosition().getX(),
                    SQUARE_SIZE * unit.getPosition().getY(), this);
        }

        //Upgrade progressing
        if (game.choosingUnit) {
            chooseUnit(g);
        }
    }

    private void drawMenu(Graphics g) {
        int y = startY;

        Font font = new Font("Arial", Font.BOLD, 30);
        FontMetrics metr = getFontMetrics(font);

        g.setFont(font);
        g.setColor(Color.black);

        String[] texts = {"SINGLE PLAYER", "MULTI PLAYER", "SETTINGS", "HELP", "QUIT"};

        for (String text : texts) {
            g.fillRect(startX, y, width, height);
            g.setColor(Color.white);
            g.fillRect(startX + 2, y + 2, width - 4, height - 4);
            g.setColor(Color.black);

            g.drawString(text, startX + (width - metr.stringWidth(text)) / 2, y + metr.getHeight() / 4 + height / 2);

            y += height + margin;
        }
    }

    private void drawSettings(Graphics g) {
        int y = startY;

        Font font = new Font("Arial", Font.BOLD, 30);
        FontMetrics metr = getFontMetrics(font);

        g.setFont(font);
        g.setColor(Color.black);

        for (VisualSetting setting : settingButtons) {
            int gap = startX + width + valueMargin;
            if (!setting.show()) continue; //Show only which must be

            g.fillRect(startX, y, width, height);
            g.setColor(Color.white);
            g.fillRect(startX + 2, y + 2, width - 4, height - 4);
            g.setColor(Color.black);

            g.drawString(setting.getName(), startX + (width - metr.stringWidth(setting.getName())) / 2
                    , y + metr.getHeight() / 4 + height / 2);

            //value
            if (setting.showValue()) {
                g.setColor(Color.white);
                g.fillRect(startX + width + valueMargin, y, valueWidth, valueHeight);
                g.setColor(Color.black);

                g.drawString(("" + setting.getValue())
                        , startX + width + valueMargin + (valueWidth - metr.stringWidth("" + setting.getValue())) / 2
                        , y + metr.getHeight() / 4 + valueHeight / 2);
                gap += valueWidth + valueMargin;
            }

            //arrows
            if (setting.hasChangeArrows()) {
                drawArrow(g, gap, y, valueWidth, true);
                drawArrow(g, gap + valueWidth + valueMargin, y, valueWidth, false);
            }

            y += height + margin;
        }
    }

    private void drawHelp(Graphics g, int page) {
        drawBoard(g);
        HelpScreen screen = HelpScreen.getScreen(page);
        if (screen == null) {
            screenId = 0;
            repaint();
            return;
        }
        screen.draw(g, this);
    }

    private void drawBackgroundUnits(Graphics g) {
        for (BackgroundUnit unit : backGroundUnits) {
            g.drawImage(unit.getType().getImage(unit.isWhite()), unit.getPosition().getX(), unit.getPosition().getY(), this);
        }
    }

    /**
     * If @param facing is true it will be facing upwards
     * and if false downwards.
     */
    public static void drawArrow(Graphics g, int x, int y, int size, boolean facing) {
        g.setColor(Color.black);
        g.fillRect(x, y, size, size);
        g.setColor(Color.white);
        g.fillRect(x + 2, y + 2, size - 4, size - 4);

        int[] xPoses = {x + 4, x + size / 2, x + size - 4, x + size / 8 * 5 + 2, x + size / 8 * 5 + 2, x + size / 8 * 3 + 2, x + size / 8 * 3 + 2};
        int[] yPoses;
        int s = 7;
        if (facing) { // Adding arrow coordinates
            yPoses = new int[]{y + size / 2, y + 4, y + size / 2, y + size / 2, y + size - 4, y + size - 4, y + size / 2};
        } else {
            yPoses = new int[]{y + size / 2, y + size - 4, y + size / 2, y + size / 2, y + 4, y + 4, y + size / 2};
        }

        g.setColor(Color.black);
        Polygon arrow = new Polygon(xPoses, yPoses, s);
        g.fillPolygon(arrow);
    }

    /**
     * If @param facing is true it will be facing left
     * and if false right.
     */
    public static void drawSidewaysArrow(Graphics g, int x, int y, int size, boolean facing) {
        g.setColor(Color.black);
        g.fillRect(x, y, size, size);
        g.setColor(Color.white);
        g.fillRect(x + 2, y + 2, size - 4, size - 4);

        int[] xPoses;
        int[] yPoses = {y + 4, y + size / 2, y + size - 4, y + size / 8 * 5 + 2, y + size / 8 * 5 + 2, y + size / 8 * 3 + 2, y + size / 8 * 3 + 2};
        int s = 7;
        if (facing) { // Adding arrow coordinates
            xPoses = new int[]{x + size / 2, x + 4, x + size / 2, x + size / 2, x + size - 4, x + size - 4, x + size / 2};
        } else {
            xPoses = new int[]{x + size / 2, x + size - 4, x + size / 2, x + size / 2, x + 4, x + 4, x + size / 2};
        }

        g.setColor(Color.black);
        Polygon arrow = new Polygon(xPoses, yPoses, s);
        g.fillPolygon(arrow);
    }

    private void drawGameOver(Graphics g, Boolean whiteWon) {

        String msg;

        if (whiteWon) {
            msg = "YOU WON!";
        } else {
            msg = "YOU LOST!";
        }

        Font font = new Font("Arial", Font.BOLD, 100);
        FontMetrics metr = getFontMetrics(font);

        g.setColor(Color.white);
        g.setFont(font);
        g.drawString(msg, (WIDTH - metr.stringWidth(msg)) / 2, HEIGHT / 2 + metr.getHeight() / 4);
    }

    private void chooseUnit(Graphics g) {
        ArrayList<Unit> possibles = game.getCaptured(!game.isWhitesTurn(), true, true);

        if (possibles.size() == 0) {
            game.choosingUnit = false;
            repaint();
            return;
        }

        int selectionMargin = 10;
        final int width = selectionMargin * 2 + possibles.size() * SQUARE_SIZE;
        final int height = selectionMargin * 2 + SQUARE_SIZE;

        selectionX = (WIDTH - width) / 2;
        selectionY = (HEIGHT - height) / 2;
        selectionSize = possibles.size();

        final Color bgColor = new Color(230, 208, 65);

        { //background
            g.setColor(bgColor);
            g.fillRect(selectionX, selectionY, width, height);
        }

        { //units
            for (int i = 0; i < selectionSize; i++) {
                Unit unit = possibles.get(i);

                g.drawImage(unit.getType().getImage(!game.isWhitesTurn()), selectionX + selectionMargin + i * SQUARE_SIZE, selectionY + selectionMargin, this);
            }

        }

        { //Text
            String msg = "WHICH DO YOU UPGRADE TO";

            Font font = new Font("Arial", Font.BOLD, 25);
            FontMetrics metr = getFontMetrics(font);

            g.setColor(bgColor);
            g.fillRect((WIDTH - metr.stringWidth(msg)) / 2 - 5, selectionY - 20, metr.stringWidth(msg) + 10, 40);

            g.setColor(Color.black);
            g.setFont(font);
            g.drawString(msg, (WIDTH - metr.stringWidth(msg)) / 2, selectionY + 10);
        }
    }


    private class MouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            boolean isOnValidY = y <= startY + 5 * (height + margin) - margin;
            switch (screenId) {
                case 0: //Main settings
                    if (e.getButton() != MouseEvent.BUTTON1) return; // Was it clicked with left button
                    if (x >= startX && x <= startX + width && y >= startY && isOnValidY) { // In settings area
                        int yPos = y - startY;
                        int size = height + margin;
                        if (yPos % size < height) { // Not in margin
                            switch (yPos / size) {
                                case 0 -> {
                                    game = new Game(settings, false); //TODO: ADD AI
                                    screenId = 1;
                                    gameOn = true;
                                    backGroundUnits.clear();
                                }
                                case 1 -> {
                                    game = new Game(settings, true);
                                    screenId = 1;
                                    gameOn = true;
                                    backGroundUnits.clear();
                                }
                                case 2 -> {
                                    screenId = 2;
                                    backGroundUnits.clear();
                                }
                                case 3 -> screenId = 3;
                                case 4 -> Chess.WINDOW.dispose();
                            }
                        }
                    }
                    break;
                case 1: //Game
                    if (!game.choosingUnit) {
                        if (e.getButton() == MouseEvent.BUTTON1) game.screenClicked(e.getX(), e.getY());
                        if (e.getButton() == MouseEvent.BUTTON3) game.unselect();
                        if (e.getButton() == 5) game.undo();
                    } else {
                        if (e.getButton() != MouseEvent.BUTTON1 || selectionSize == 0)
                            return; // Checking was it clicked with left button or is the game waiting for player to choose which to  upgrade.

                        if (x < selectionX || x > selectionX + selectionSize * SQUARE_SIZE)
                            return; // Checking is click inside options
                        if (y < selectionY || y > selectionY + SQUARE_SIZE) return;

                        game.select(x - selectionX);
                    }
                    break;
                case 2: //Settings
                    if (e.getButton() != MouseEvent.BUTTON1) return; // Was it clicked with left button
                    if (x >= startX && y >= startY && isOnValidY) { // In settings area
                        if (x <= startX + width) { // Clicked button
                            int yPos = y - startY;
                            int size = height + margin;
                            if (yPos % size < height) { // Not in margin
                                ArrayList<VisualSetting> showing = new ArrayList<>();
                                for (VisualSetting visualSetting : settingButtons) {
                                    if (visualSetting.show()) showing.add(visualSetting);

                                }
                                VisualSetting clicked = showing.get(yPos / size);
                                if (!clicked.hasChangeArrows()) clicked.add();
                                clicked.pressed(Chess.WINDOW.PANEL);
                            }
                        } else {
                            int xPos = x - startX - width - valueMargin;
                            int yPos = y - startY;

                            int size = height + margin;
                            if (yPos % size < height) { // Not in margin

                                int xId;
                                if (xPos >= 0 && xPos <= valueWidth) { //first
                                    xId = 1;
                                } else if (xPos >= valueWidth + valueMargin && xPos <= 2 * valueWidth + valueMargin) { //second
                                    xId = 2;
                                } else if (xPos >= 2 * valueWidth + 2 * valueMargin && xPos <= 3 * valueWidth + 2 * valueMargin) { //third
                                    xId = 3;
                                } else { // none
                                    break;
                                }

                                ArrayList<VisualSetting> showing = new ArrayList<>();
                                for (VisualSetting visualSetting : settingButtons) {
                                    if (visualSetting.show()) showing.add(visualSetting);

                                }
                                VisualSetting clicked = showing.get(yPos / size);

                                if (!clicked.showValue()) xId++; //If value box is hidden add 1

                                switch (xId) { // if xId == 1 value box was clicked
                                    case 2 -> clicked.add();
                                    case 3 -> clicked.reduce();
                                }
                            }
                        }
                    }
                    break;
                default:
                    if (e.getButton() != MouseEvent.BUTTON1) return; // Was it clicked with left button
                    int buttonClicked = HelpScreen.getButtonId(new Position(e.getX(), e.getY()));
                    switch (buttonClicked) {
                        case 1 -> screenId = 0;
                        case 2 -> {
                            if (screenId > 3) screenId--;
                        }
                        case 3 -> {
                            if (screenId < HelpScreen.getTotalScreens() + 2) screenId++;
                        }
                    }

            }

            repaint();
        }

    }

}
