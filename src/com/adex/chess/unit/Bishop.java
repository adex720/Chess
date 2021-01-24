package com.adex.chess.unit;

import com.adex.chess.game.Position;
import com.adex.chess.unit.movement.MovementType;

public class Bishop extends Unit{

    public Bishop(Position position, boolean white) {
        super(position, Type.BISHOP, white);
        addMovementType(new MovementType(MovementType.Type.DIAGONAL));
    }


}
