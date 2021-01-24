package com.adex.chess.unit.movement;

import com.adex.chess.game.Game;
import com.adex.chess.game.Position;
import com.adex.chess.unit.Unit;

import java.util.ArrayList;

public abstract class Undo {

    private static Unit lastMoved;
    private static Unit lastMoved2;
    private static Position lastPosition;
    private static Position lastPosition2;

    private static Unit lastRemoved;
    private static Unit lastAdded;

    public static void undo(Game game) {
        ArrayList<Unit> units = game.getUnits();

        if (lastRemoved != null) {
            units.add(lastRemoved);
            game.removeEaten(lastRemoved);
        }

        if (lastAdded != null) {
            units.remove(lastAdded);
            game.addEaten(lastAdded, lastAdded.isWhite());
        }

        for (Unit unit : units) {
            if (unit.equals(lastMoved)) {
                unit.setPosition(lastPosition);
                unit.setTimesMoved(unit.getTimesMoved() - 1);
            }
            if (unit.equals(lastMoved2)) {
                unit.setPosition(lastPosition2);
                unit.setTimesMoved(unit.getTimesMoved() - 1);
            }
        }

        lastMoved = null;
        lastMoved2 = null;
        lastPosition = null;
        lastPosition2 = null;
        lastRemoved = null;
        lastAdded = null;
    }

    /**
     * Always run moved() first.
     * */
    public static void moved(Unit lastMovedUnit) {
        lastMoved = lastMovedUnit;
        lastPosition = lastMovedUnit.getPosition();
        lastRemoved = null;
        lastAdded = null;
        lastMoved2 = null;
        lastPosition2 = null;
    }

    /**
     * Use this to add multiple undoable units or to add new one without resetting eaten or upgraded ones.
     * */
    public static void moved2 (Unit lastMoved){
        lastMoved2 = lastMoved;
        lastPosition2 = lastMoved.getPosition();
    }

    public static void addRemoved(Unit eaten) {
        lastRemoved = eaten;
    }

    public static void addAdded(Unit upgraded) {
        lastAdded = upgraded;
    }

}
