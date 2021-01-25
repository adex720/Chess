package com.adex.chess.game;

import com.adex.chess.unit.Type;
import com.adex.chess.unit.Unit;
import com.adex.chess.unit.movement.Undo;

import java.util.ArrayList;

public class AI {

    public AI() { //AI always black

    }

    public void makeMove(Game game) {
        ArrayList<Unit> aIUnits = game.getUnits(false);
        Unit aIKing = game.getKing(false);

        if (game.isUnitAtRisk(aIKing)) {
            ArrayList<Unit> riskingKing = game.getUnitsRisking(aIKing);
            if (riskingKing.size() == 1) { //Can checking unit be eaten.
                Unit risking = riskingKing.get(0);
                for (Unit unit : aIUnits) {
                    if (unit.canMove(unit.getPosition(), risking.getPosition(), game)) { // Can unit capture the checking unit
                        if (unit.getType() != Type.KING || game.isUnitAtRisk(aIKing, risking.getPosition())) { // If unit is king it does be in check after capturing
                            unit.setPosition(risking.getPosition());
                            unit.moved(game);
                            game.captured(risking);

                            Undo.moved(unit);
                            return;
                        }
                    }
                }

            }

            int[] x = {-1, 1, 0 , -1, 1, -1, 1, 0}; //Can king move to another tile
            int[] y = {1, 1, 1, 0, 0, -1, -1, -1}; //Prioritizing positions which are most likely safe.
            for (int i = 0; i < 8; i++) {
                Position position = new Position(aIKing.getPosition().getX() + x[i], aIKing.getPosition().getY() + y[i]);
                if (aIKing.canMove(aIKing.getPosition(), position, game)
                        && !game.isUnitAtRisk(aIKing, position)) {
                    if (game.hasUnitIn(position)) {
                        Unit movingTo = game.getUnitOnPosition(position);
                        game.captured(movingTo);
                    }
                    aIKing.setPosition(position);
                    aIKing.moved(game);
                    Undo.moved(aIKing);

                    return;
                }
            }

            if (riskingKing.size() == 1) {
                for (Unit unit : aIUnits) { //Can an unit be moved between king and the unit checking.
                    for (Position position : getPositionsBetween(riskingKing.get(0).getPosition(), aIKing.getPosition())) {
                        if (unit.canMove(unit.getPosition(), position, game)){
                            unit.setPosition(position); //There can't be unit on that tile so there's no point checking it.
                            unit.moved(game);
                            Undo.moved(unit);

                            return;
                        }
                    }
                }
            }

            //AI lost
            return;
        }
    }

    private ArrayList<Position> getPositionsBetween(Position pos1, Position pos2) {
        ArrayList<Position> positions = new ArrayList<>();

        if (pos1.getX() == pos2.getX()) { // same X
            int mult = 1;
            if (pos1.getY() > pos2.getY()) mult = -1;
            for (int y = pos1.getY() + mult; y != pos2.getY(); y += mult) {
                positions.add(new Position(pos1.getX(), y));
            }
        } else if (pos1.getY() == pos2.getY()) { // same Y
            int mult = 1;
            if (pos1.getX() > pos2.getX()) mult = -1;
            for (int x = pos1.getX() + mult; x != pos2.getX(); x += mult) {
                positions.add(new Position(x, pos1.getY()));
            }
        } else if (pos1.getXDifferenceTo(pos2) == pos1.getYDifferenceTo(pos2)) { // same diagonal line
            int xMult = 1;
            if (pos1.getX() > pos2.getX()) xMult = -1;
            int yMult = 1;
            if (pos1.getY() > pos2.getY()) yMult = -1;
            int y = pos1.getY() + yMult;
            for (int x = pos1.getX() + xMult; x != pos2.getX(); x += xMult) {
                positions.add(new Position(x, y));
                y += yMult;
            }
        }
        return positions;
    }
}
