package com.adex.chess.unit.movement;

import com.adex.chess.game.Game;
import com.adex.chess.game.Position;
import com.adex.chess.unit.Unit;

public class MovementType {

    private final Type type;
    private final int max;

    private static boolean WAS_LAST_CAPTURED;

    public MovementType(Type type, int max) {
        this.type = type;
        this.max = max;
    }

    public MovementType(Type type) {
        this.type = type;
        max = 10;
    }

    public Type getType() {
        return type;
    }

    public int getMax() {
        return max;
    }

    public static boolean wasLastCaptured() {
        if (WAS_LAST_CAPTURED) {
            WAS_LAST_CAPTURED = false;
            return true;
        }

        return false;
    }

    public boolean canMove(Position startPos, Position endPos, boolean isWhite, Game game) {
        if (startPos.equals(endPos)) return false;

        if (type == Type.FORWARD) {

            int modifier = 1;
            if (isWhite) modifier = -1;

            { // Capturing
                Unit selected = game.getSelected();
                Unit movingTo = game.getUnitOnPosition(endPos);

                if (movingTo != null &&  selected != null && movingTo.getType() != com.adex.chess.unit.Type.KING) { //Let's not throw 1 000 000 NullPointerExceptions.
                    if (movingTo.isWhite() != isWhite //Why would you even do that?
                            && selected.getType() == com.adex.chess.unit.Type.PAWN && movingTo.getType() == com.adex.chess.unit.Type.PAWN //Only pawn can capture and only pawn can be captured
                            && movingTo.getTimesMoved() == 1) {
                        if (startPos.getY() == endPos.getY()
                                && Game.getDifference(startPos.getX(), endPos.getX()) == 1) {
                            game.addEaten(movingTo, movingTo.isWhite());
                            WAS_LAST_CAPTURED = true;
                            return true;
                        }
                    }
                }
            }

            if (startPos.getX() == endPos.getX() && startPos.getY() + modifier == endPos.getY() && !game.hasUnitIn(endPos))
                return true;
            if ((startPos.getY() == 1 || startPos.getY() == 6) && startPos.getX() == endPos.getX() //Is unit at start position
                    && startPos.getY() + 2 * modifier == endPos.getY() //Was it set to move 2
                    && !game.hasUnitIn(endPos) && !game.hasUnitIn(endPos.add(0, modifier * -1)))
                return true; //Is there no units on the way

            if (startPos.getY() + modifier == endPos.getY() && game.hasUnitIn(endPos)) {
                if (startPos.getX() - 1 == endPos.getX() && game.getUnitOnPosition(endPos).isWhite() != isWhite)
                    return true;
                return startPos.getX() + 1 == endPos.getX() && game.getUnitOnPosition(endPos).isWhite() != isWhite;
            }

            return false;
        }


        if (type == Type.STRAIGHT) {
            if (startPos.getX() != endPos.getX() && startPos.getY() != endPos.getY()) return false;
            if (startPos.getXDifferenceTo(endPos) > max || -startPos.getXDifferenceTo(endPos) > max) return false;
            if (startPos.getYDifferenceTo(endPos) > max || -startPos.getYDifferenceTo(endPos) > max) return false;

            if (startPos.getX() == endPos.getX()) {
                if (startPos.getY() < endPos.getY()) {
                    for (int y = startPos.getY() + 1; y < endPos.getY(); y++) {
                        if (game.hasUnitIn(startPos.getX(), y)) return false;
                    }
                } else {
                    for (int y = startPos.getY() - 1; y > endPos.getY(); y--) {
                        if (game.hasUnitIn(startPos.getX(), y)) return false;
                    }
                }
            } else {

                if (startPos.getX() < endPos.getX()) {
                    for (int x = startPos.getX() + 1; x < endPos.getX(); x++) {
                        if (game.hasUnitIn(x, startPos.getY())) return false;

                    }
                } else {
                    for (int x = startPos.getX() - 1; x > endPos.getX(); x--) {
                        if (game.hasUnitIn(x, startPos.getY())) return false;
                    }
                }
            }

            return true;
        }

        if (type == Type.DIAGONAL) {
            int xDiff = startPos.getXDifferenceTo(endPos.getX());
            int yDiff = startPos.getYDifferenceTo(endPos.getY());
            if (xDiff == yDiff || -xDiff == yDiff) {
                int xMult = 1;
                int yMult = 1;
                if (xDiff < 0) xMult = -1;
                if (yDiff < 0) yMult = -1;

                for (int step = 1; step <= max; step++) {
                    Position current = startPos.add(step * xMult, step * yMult);

                    if (current.equals(endPos)) return true;
                    if (game.hasUnitIn(current)) return false;
                }

                return false;
            }

            return false;
        }


        if (type == Type.KNIGHT) {

            return Math.pow(startPos.getX() - endPos.getX(), 2) + Math.pow(startPos.getY() - endPos.getY(), 2) == 5;
            //It can't be that simple!!! -Well try it, it does work, right?

            /*int startX = startPos.getX();
            int startY = startPos.getY();
            int endX = endPos.getX();
            int endY = endPos.getY();
            return startX - 2 == endX && startY - 1 == endY
                    || startX + 2 == endX && startY - 1 == endY
                    || startX + 2 == endX && startY + 1 == endY
                    || startX - 2 == endX && startY + 1 == endY
                    || startX - 1 == endX && startY - 2 == endY
                    || startX + 1 == endX && startY - 2 == endY
                    || startX + 1 == endX && startY + 2 == endY
                    || startX - 1 == endX && startY + 2 == endY;*/
        }

        if (type == Type.KING) {

            { //Castling
                if (endPos.getX() == 0 && (endPos.getY() == 0 || endPos.getY() == 7)) { //Is it moving to a valid position.
                    Unit selected = game.getSelected();
                    Unit movingTo = game.getUnitOnPosition(endPos);

                    if (selected != null && movingTo != null) {
                        if (selected.getTimesMoved() == 0 && movingTo.getTimesMoved() == 0) { //Both haven't moved before.
                            if (!game.hasUnitIn(new Position(1, startPos.getY())) && !game.hasUnitIn(new Position(2, startPos.getY()))) { //There isn't units before them.
                                if (!game.isUnitAtRisk(selected, new Position(1, startPos.getY())) //The king is not in danger during the move.
                                        && !game.isUnitAtRisk(selected, new Position(2, startPos.getY()))
                                        && !game.isUnitAtRisk(selected, new Position(3, startPos.getY()))) {

                                    Undo.moved(selected);
                                    Undo.moved2(movingTo);

                                    selected.setPosition(new Position(1, startPos.getY()));
                                    movingTo.setPosition(new Position(2, startPos.getY()));

                                    game.changeTurn();
                                }
                            }
                        }
                    }
                }

                return false;
            }
        }

        return true;
    }


    public enum Type {
        STRAIGHT, DIAGONAL, FORWARD, KNIGHT, KING
    }

}
