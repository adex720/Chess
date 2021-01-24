package com.adex.chess.game;

import com.adex.chess.visual.Panel;

public class Position {

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Position getPositionFromScreenPosition(int x, int y) {
        x /= Panel.SQUARE_SIZE;
        y /= Panel.SQUARE_SIZE;
        return new Position(x, y);
    }

    public static Position getPositionFromScreenPosition(Position positionOnScreen) {
        int x = positionOnScreen.getX() / Panel.SQUARE_SIZE;
        int y = positionOnScreen.getY() / Panel.SQUARE_SIZE;
        return new Position(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Position getPositionOnScreen() {
        return new Position(x * Panel.SQUARE_SIZE, y * Panel.SQUARE_SIZE);
    }

    public static Position getPositionOnScreen(Position position) {
        return new Position(position.getX() * Panel.SQUARE_SIZE, position.getY() * Panel.SQUARE_SIZE);
    }

    public int getXDifferenceTo(int x) {
        return x - this.x;
    }

    public int getXDifferenceTo(int x, int y) {
        return getXDifferenceTo(x);
    }

    public int getXDifferenceTo(Position position) {
        return getXDifferenceTo(position.getX());
    }

    public int getYDifferenceTo(int y) {
        return y - this.y;
    }

    public int getYDifferenceTo(int x, int y) {
        return getYDifferenceTo(y);
    }

    public int getYDifferenceTo(Position position) {
        return getYDifferenceTo(position.getY());
    }

    public Position add(int x, int y) {
        return new Position(this.x + x, this.y + y);
    }

    public static int getDistanceToWall(int x, int y) {
        if (x == 0 || x == 7 || y == 0 || y == 7) return 1;
        if (x == 1 || x == 6 || y == 1 || y == 6) return 2;
        if (x == 2 || x == 5 || y == 2 || y == 5) return 3;
        if (x == 3 || x == 4 || y == 3 || y == 4) return 4;

        return 0;
    }

    public static int getDistanceToWall(Position position) {
        return getDistanceToWall(position.getX(), position.getY());
    }

    public int getDistanceToWall() {
        return getDistanceToWall(this);
    }

    @Override
    public boolean equals(Object obj) {
        try {
            Position position = (Position) obj;
            return position.getX() == x && position.getY() == y;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return "x: " + x + ", y: " + y;
    }
}
