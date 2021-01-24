package com.adex.chess.game;

import com.adex.chess.Chess;
import com.adex.chess.settings.Settings;
import com.adex.chess.unit.*;
import com.adex.chess.unit.movement.MovementType;
import com.adex.chess.unit.movement.Undo;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.adex.chess.game.Position.getPositionFromScreenPosition;
import static com.adex.chess.visual.Panel.SQUARE_SIZE;

public class Game {

    private final ArrayList<Unit> units;
    private final ArrayList<Unit> cacheUnits;

    private Unit selected = null;

    public boolean gameOver;
    public boolean isWhiteWinner;

    private boolean whitesTurn;

    private int undosWhite;
    private int undosBlack;

    private final ArrayList<Unit> eatenWhite;
    private final ArrayList<Unit> eatenBlack;

    private Unit chosen;
    private Pawn updating;
    public boolean choosingUnit;

    public final boolean multiPlayer;


    private final boolean usingTimer;
    private int timeLeftWhite;
    private int timeLeftBlack;

    private Timer timer;

    public Game(Settings settings, boolean multiPlayer) {
        units = new ArrayList<>();
        this.multiPlayer = multiPlayer;

        addUnits();
        gameOver = false;
        whitesTurn = true;
        choosingUnit = false;

        undosWhite = settings.getUndos();
        undosBlack = settings.getUndos();
        eatenWhite = new ArrayList<>();
        eatenBlack = new ArrayList<>();

        cacheUnits = new ArrayList<>();

        chosen = null;
        updating = null;

        usingTimer = settings.getTime() != 0;
        if (!usingTimer) {
            timeLeftWhite = 0;
            timeLeftBlack = 0;
        } else {
            timeLeftWhite = settings.getTime(); // 60 seconds in 1 minute
            timeLeftBlack = settings.getTime() * 60;
            startTimer();
        }
    }

    /*public Game(int undos) {
        units = new ArrayList<>();

        addUnits();
        gameOver = false;
        whitesTurn = true;
        choosingUnit = false;

        undosWhite = undos;
        undosBlack = undos;
        eatenWhite = new ArrayList<>();
        eatenBlack = new ArrayList<>();

        cacheUnits = new ArrayList<>();

        chosen = null;
    }*/

    private void addUnits() {

        for (int x = 0; x < 8; x++) {
            units.add(new Pawn(new Position(x, 1), false));
        }
        units.add(new Rook(new Position(0, 0), false));
        units.add(new Knight(new Position(1, 0), false));
        units.add(new Bishop(new Position(2, 0), false));
        units.add(new King(new Position(3, 0), false));
        units.add(new Queen(new Position(4, 0), false));
        units.add(new Bishop(new Position(5, 0), false));
        units.add(new Knight(new Position(6, 0), false));
        units.add(new Rook(new Position(7, 0), false));

        for (int x = 0; x < 8; x++) {
            units.add(new Pawn(new Position(x, 6), true));
        }
        units.add(new Rook(new Position(0, 7), true));
        units.add(new Knight(new Position(1, 7), true));
        units.add(new Bishop(new Position(2, 7), true));
        units.add(new King(new Position(3, 7), true));
        units.add(new Queen(new Position(4, 7), true));
        units.add(new Bishop(new Position(5, 7), true));
        units.add(new Knight(new Position(6, 7), true));
        units.add(new Rook(new Position(7, 7), true));
    }


    private void startTimer() {
        timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                reduceSecond();
            }
        }, 0, 1000);
    }

    private void stopTimer() {
        timer.cancel();
    }

    public boolean hasTimer() {
        return usingTimer;
    }

    public Unit getSelected() {
        return selected;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public ArrayList<Unit> getUnits(boolean whites) {
        ArrayList<Unit> corrects = new ArrayList<>();

        for (Unit unit : units) {
            if (unit.isWhite() == whites) corrects.add(unit);
        }

        return corrects;
    }

    public Unit getUnitOnPosition(Position position) {

        for (Unit unit : units) {
            if (position.equals(unit.getPosition())) {
                return unit;
            }
        }

        return null;
    }

    public void addCacheUnit(Unit unit) {
        cacheUnits.add(unit);
        units.add(unit);
    }

    public void clearCacheUnits() {
        for (Unit unit : cacheUnits) {
            units.remove(unit);
        }

        cacheUnits.clear();
    }

    public Unit getUnitOnScreenPosition(Position position) {

        position = getPositionFromScreenPosition(position);

        for (Unit unit : units) {
            if (position.equals(unit.getPosition())) {
                return unit;
            }
        }

        return null;
    }

    public Unit getUnitOnPosition(int x, int y) {
        return getUnitOnPosition(new Position(x, y));
    }

    public Unit getUnitOnScreenPosition(int x, int y) {
        return getUnitOnScreenPosition(new Position(x, y));
    }

    public boolean hasUnitIn(Position position) {
        return getUnitOnPosition(position) != null;
    }

    public boolean hasUnitIn(int x, int y) {
        return getUnitOnPosition(new Position(x, y)) != null;
    }

    public boolean hasUnitInScreenPosition(Position position) {
        return getUnitOnScreenPosition(position) != null;
    }

    public boolean hasUnitInScreenPosition(int x, int y) {
        return getUnitOnScreenPosition(new Position(x, y)) != null;
    }

    public Unit getWhiteKing() {
        for (Unit unit : units) {
            if (unit.getType() == Type.KING && unit.isWhite()) {
                return unit;
            }
        }
        return null;
    }

    public Unit getBlackKing() {
        for (Unit unit : units) {
            if (unit.getType() == Type.KING && !unit.isWhite()) {
                return unit;
            }
        }

        return null;
    }

    public Unit getKing(boolean white) {
        if (white) return getWhiteKing();
        return getBlackKing();
    }

    public boolean isWhitesTurn() {
        return whitesTurn;
    }

    public int getTimeLeft(boolean white) {
        if (white) return timeLeftWhite;
        return timeLeftBlack;
    }

    private void reduceSecond() {
        if (whitesTurn) {
            timeLeftWhite--;
            if (timeLeftWhite == 0) {
                gameOver = true;
                isWhiteWinner = false;
                stopTimer();
            }
        } else {
            timeLeftBlack--;
            if (timeLeftBlack == 0) {
                gameOver = true;
                isWhiteWinner = true;
                stopTimer();
            }
        }

        Chess.WINDOW.repaint();
    }

    public void removeEaten(Unit lastEaten) {
        if (lastEaten.isWhite()) eatenWhite.remove(lastEaten);
        else eatenBlack.remove(lastEaten);
    }

    public boolean isUnitAtRisk(Unit unit) {
        for (Unit unit1 : getUnits(!unit.isWhite())) {
            if (unit1.canMove(unit1.getPosition(), unit.getPosition(), this)) return true;
        }

        return false;
    }

    public boolean isUnitAtRisk(Unit unit, Position atPosition) {
        for (Unit unit1 : getUnits(!unit.isWhite())) {
            if (unit1.canMove(unit1.getPosition(), atPosition, this)) return true;
        }

        return false;
    }

    public boolean isUnitAtRisk(Unit atRisk, Unit selectedUnit, Position atPosition) {
        Position startPosition = selectedUnit.getPosition();
        selectedUnit.setPosition(atPosition);

        for (Unit unit1 : getUnits(!atRisk.isWhite())) {
            if (unit1.canMove(unit1.getPosition(), atRisk.getPosition(), this) && !unit1.getPosition().equals(atPosition)) {
                selectedUnit.setPosition(startPosition);
                return true;
            }
        }

        selectedUnit.setPosition(startPosition);
        return false;
    }

    public ArrayList<Unit> getUnitsRisking(Unit unit) {
        ArrayList<Unit> risking = new ArrayList<>();

        for (Unit unit1 : units) {
            if (unit1.canMove(unit1.getPosition(), unit.getPosition(), this) && unit.isWhite() != unit1.isWhite())
                risking.add(unit1);
        }

        return risking;
    }

    public void addEaten(Unit eaten, boolean isWhite) {
        if (isWhite) eatenWhite.add(eaten);
        else eatenBlack.add(eaten);
    }

    public ArrayList<Unit> getEaten(boolean white, boolean onlyUniques, boolean notPawnsOrKings) {
        ArrayList<Unit> uniques = new ArrayList<>();
        ArrayList<Unit> units;

        if (white) units = eatenWhite;
        else units = eatenBlack;

        for (Unit unit : units) {
            boolean unique = true;
            for (Unit unit1 : uniques) {
                if (unit1.getType() == unit.getType()) {
                    unique = false;
                    break;
                }
            }
            if (!(unique && onlyUniques)) continue;
            if (notPawnsOrKings && (unit.getType().equals(Type.PAWN) || unit.getType().equals(Type.KING))) continue;
            uniques.add(unit);
        }
        return uniques;
    }

    public ArrayList<Unit> getEaten(boolean white, boolean onlyUniques) {
        return getEaten(white, onlyUniques, false);
    }

    public ArrayList<Unit> getEaten(boolean white) {
        if (white) {
            return eatenWhite;
        }

        return eatenBlack;
    }

    //moving
    public void screenClicked(int x, int y) {
        Position clicked = getPositionFromScreenPosition(x, y);

        if (selected == null) {
            selected = getUnitOnPosition(clicked);

            try {
                if (selected.isWhite() != whitesTurn) selected = null;
            } catch (Exception ignored) {

            }
            return;
        }

        if (selected.canMove(selected.getPosition(), clicked, this)) {
            if (MovementType.wasLastCaptured()) {
                Unit movingTo = this.getUnitOnPosition(clicked);

                if (selected.isWhite()) clicked = clicked.add(0, -1);
                else clicked = clicked.add(0, 1);

                if (movingTo.isWhite()) {
                    eatenWhite.add(movingTo);
                } else {
                    eatenBlack.add(movingTo);
                }

                Undo.moved(selected);
                Undo.addRemoved(movingTo);

                selected.setPosition(clicked);
                units.remove(movingTo);

                changeTurn();
                return;
            }

            if (selected.hasMovementType(MovementType.Type.KING)) {
                if (isUnitAtRisk(selected, clicked)) return;
            }

            if (isUnitAtRisk(getKing(selected.isWhite()), selected, clicked)) return; //Is king in danger after the move

            if (!this.hasUnitIn(clicked)) {
                Undo.moved(selected);
                selected.setPosition(clicked);
                selected.moved(this);
            } else {
                Unit movingTo = this.getUnitOnPosition(clicked);
                if (movingTo.isWhite() != selected.isWhite()) {
                    if (movingTo.getType().equals(Type.KING)) {
                        gameOver = true;
                        isWhiteWinner = !movingTo.isWhite();
                    }

                    Undo.moved(selected);
                    Undo.addRemoved(movingTo);

                    if (movingTo.isWhite()) {
                        eatenWhite.add(movingTo);
                    } else {
                        eatenBlack.add(movingTo);
                    }
                    units.remove(movingTo);

                    selected.setPosition(clicked);
                    selected.moved(this);
                }
            }

            changeTurn();
        }
    }

    public void changeTurn() {

        //is king at checkmate
        boolean canMove = true;

        Unit king = getKing(!whitesTurn);
        int[] x = {-1, 0, 1, -1, 1, -1, 0, 1};
        int[] y = {1, 1, 1, 0, 0, -1, -1, -1};
        for (int i = 0; i < 8; i++) {
            Position endPos = king.getPosition().add(x[i], y[i]);
            if (isOnBoard(endPos)) continue;

            if (!isUnitAtRisk(king, endPos)) {
                if (hasUnitIn(endPos))
                    if (getUnitOnPosition(endPos).isWhite() == king.isWhite()) continue;
                canMove = false;
            }
        }

        if (canMove && selected != null) { // fixing npe when undoing
            if (isUnitAtRisk(king)) {
                gameOver = true;
                isWhiteWinner = whitesTurn;
                stopTimer();
            } else {
                if (getUnits(!selected.isWhite()).size() == 1) { //stalemate
                    gameOver = true;
                    stopTimer();
                }
            }
        }

        whitesTurn = !whitesTurn;
        selected = null;
        Chess.WINDOW.repaint();
    }

    public void unselect() {
        selected = null;
    }

    public void select(int x) {
        x /= SQUARE_SIZE;

        ArrayList<Unit> possibles = getEaten(!isWhitesTurn(), true, true);
        chosen = possibles.get(x);
        choosingUnit = false;

        upDate(updating, chosen.getType());
    }

    public void undo() {
        if (whitesTurn) { //Turn not changed back yet.
            if (undosBlack-- < 1) return;
        }
        if (undosWhite-- < 1) return;

        Undo.undo(this);
        changeTurn();
    }

    public Position getSelectedScreenPosition() {
        if (selected == null) {
            return null;
        }

        return new Position(selected.getPosition().getX() * SQUARE_SIZE, selected.getPosition().getY() * SQUARE_SIZE);
    }

    public void upDate(Pawn pawn, Type to) {
        units.remove(pawn);
        Undo.addRemoved(pawn);

        if (pawn.isWhite()) {
            for (Unit unit : eatenWhite) {
                if (unit.getType() == to) {
                    unit.setPosition(pawn.getPosition());
                    units.add(unit);
                    eatenWhite.remove(unit);
                    Undo.addAdded(unit);
                    break;
                }
            }
        } else {
            for (Unit unit : eatenBlack) {
                if (unit.getType() == to) {
                    unit.setPosition(pawn.getPosition());
                    units.add(unit);
                    eatenBlack.remove(unit);
                    Undo.addAdded(unit);
                    break;
                }
            }

        }
    }

    public void choosingUnit(Pawn pawn) {
        choosingUnit = true;
        updating = pawn;
    }

    public static int getDifference(int i1, int i2) {
        if (i1 > i2) return i1 - i2;
        return i2 - i1;
    }

    public static boolean isOnBoard(Position position) {
        return !(position.getX() < 0 || position.getX() > 7 || position.getY() < 0 || position.getY() > 7);
    }
}
