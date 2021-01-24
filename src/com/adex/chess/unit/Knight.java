package com.adex.chess.unit;

import com.adex.chess.game.Position;
import com.adex.chess.unit.movement.MovementType;

public class Knight extends Unit{

    public Knight(Position position, boolean white) {
        super(position, Type.KNIGHT, white);
        addMovementType(new MovementType(MovementType.Type.KNIGHT));
    }


}
