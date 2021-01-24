package com.adex.chess.unit;

import com.adex.chess.game.Position;
import com.adex.chess.unit.movement.MovementType;

public class Rook extends Unit{

    public Rook(Position position, boolean white) {
        super(position, Type.ROOK, white);
        addMovementType(new MovementType(MovementType.Type.STRAIGHT));
    }

}
