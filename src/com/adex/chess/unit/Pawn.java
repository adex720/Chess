package com.adex.chess.unit;

import com.adex.chess.game.Game;
import com.adex.chess.game.Position;
import com.adex.chess.unit.movement.MovementType;

public class Pawn extends Unit{

    public Pawn(Position position, boolean white) {
        super(position, Type.PAWN,  white);
        addMovementType(new MovementType(MovementType.Type.FORWARD));
    }

    @Override
    public void moved(Game game) {
        super.moved(game);
        if (this.getPosition().getY() == 0 || this.getPosition().getY() == 7){
            game.choosingUnit(this);

        }
    }
}
