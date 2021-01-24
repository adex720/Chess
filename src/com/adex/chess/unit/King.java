package com.adex.chess.unit;

import com.adex.chess.game.Position;
import com.adex.chess.unit.movement.MovementType;

public class King extends Unit{

    /**
     * When making an unit which can't be moved to se it can be eaten ALWAYS add the @MovementType.Type.KING first
     * */
    public King(Position position, boolean white) {
        super(position, Type.KING,  white);
        addMovementType(new MovementType(MovementType.Type.KING)); //This must be first
        addMovementType(new MovementType(MovementType.Type.STRAIGHT, 1));
        addMovementType(new MovementType(MovementType.Type.DIAGONAL, 1));
    }

}