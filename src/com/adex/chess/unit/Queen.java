package com.adex.chess.unit;

import com.adex.chess.game.Position;
import com.adex.chess.unit.movement.MovementType;

public class Queen extends Unit{

    public Queen(Position position, boolean white) {
        super(position, Type.QUEEN,  white);
        addMovementType(new MovementType(MovementType.Type.STRAIGHT));
        addMovementType(new MovementType(MovementType.Type.DIAGONAL));
    }


}
