package com.adex.chess.unit;

import com.adex.chess.game.Game;
import com.adex.chess.game.Position;
import com.adex.chess.unit.movement.MovementType;

import java.util.ArrayList;

public abstract class Unit {

    protected Position position;

    protected final Type type;
    private final boolean white;

    private final ArrayList<MovementType> movementTypes;

    private int timesMoved;

    public Unit(Position position, Type type, boolean white) {
        this.position = position;
        this.type = type;
        this.white = white;
        this.movementTypes = new ArrayList<>();

        timesMoved = 0;
    }

    public Unit(Position position, Unit unit) {
        this.position = position;
        type = unit.getType();
        white = unit.isWhite();
        this.movementTypes = unit.getMovementTypes();

        timesMoved = 0;
    }

    public void addMovementType(MovementType movementType) {
        movementTypes.add(movementType);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Type getType() {
        return type;
    }

    public boolean isWhite() {
        return white;
    }

    public ArrayList<MovementType> getMovementTypes() {
        return movementTypes;
    }

    public boolean hasMovementType(MovementType.Type movementType) {
        for (MovementType movementType1 : movementTypes) {
            if (movementType1.getType() == movementType) return true;
        }
        return false;
    }

    public boolean canMove(Position startPos, Position endPos, Game game) {
        if (endPos.getX() < 0 || endPos.getX() > 7 || endPos.getY() < 0 || endPos.getY() > 7) return false; //You can't move outside of the board.

        if (game.hasUnitIn(endPos))
            if (game.getUnitOnPosition(endPos).isWhite() == game.getUnitOnPosition(startPos).isWhite()) return false; //You can't move somewhere where already is your unit.

        for (MovementType movementType : movementTypes) {
            if (movementType.canMove(startPos, endPos, white, game)) return true;

        }

        return false;
    }


    public void moved(Game Game) {
        timesMoved++;
    }

    public int getTimesMoved() {
        return timesMoved;
    }

    public void setTimesMoved(int timesMoved) {
        this.timesMoved = timesMoved;
    }
}
