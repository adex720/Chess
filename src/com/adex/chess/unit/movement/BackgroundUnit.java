package com.adex.chess.unit.movement;

import com.adex.chess.game.Game;
import com.adex.chess.game.Position;
import com.adex.chess.unit.Type;
import com.adex.chess.unit.Unit;
import com.adex.chess.visual.Panel;

import java.util.ArrayList;
import java.util.Random;

public class BackgroundUnit extends Unit {

    private int direction;
    private int steps;

    /**
     * 1  2  3
     * 8  ¤  4
     * 7  6  5
     */

    public BackgroundUnit(Random random) {
        super(new Position(random.nextInt(8) * Panel.SQUARE_SIZE
                        , random.nextInt(8) * Panel.SQUARE_SIZE)
                , Type.getWeightedRandom(), random.nextBoolean());

        redirect(random);
    }

    public void move() {

        switch (direction) {
            case 1 -> {
                setPosition(getPosition().add(-1, -1));
            }
            case 2 -> {
                setPosition(getPosition().add(0, -1));
            }
            case 3 -> {
                setPosition(getPosition().add(1, -1));
            }
            case 4 -> {
                setPosition(getPosition().add(1, 0));
            }
            case 5 -> {
                setPosition(getPosition().add(1, 1));
            }
            case 6 -> {
                setPosition(getPosition().add(0, 1));
            }
            case 7 -> {
                setPosition(getPosition().add(-1, 1));
            }
            case 8 -> {
                setPosition(getPosition().add(-1, 0));
            }
        }

        steps--;
        if (steps == 0) {
            redirect(new Random());
        }
    }

    public void redirect(Random random) {
        Position position = new Position(getPosition().getX() / Panel.SQUARE_SIZE, getPosition().getY() / Panel.SQUARE_SIZE);

        if (getType() == Type.ROOK) {
            moveStraight(position, random);
        } else if (getType() == Type.BISHOP) {
            moveDiagonally(position, random);
        } else if (getType() == Type.QUEEN) { //  50/50 to move straight or diagonally .
            if (random.nextBoolean()) { //straight
                moveStraight(position, random);
            } else { //diagonally
                moveDiagonally(position, random);
            }
        } else {
            moveOnce(position, random);
        }
    }

    private void moveStraight(Position currentPosition, Random random) {
        int randValue = random.nextInt(14);
        if (randValue < 7) { // changing x
            if (randValue >= currentPosition.getX()) {
                steps = (randValue + 1 - currentPosition.getX()) * Panel.SQUARE_SIZE;
                direction = 4;
            } else {
                steps = (randValue + 1) * Panel.SQUARE_SIZE;
                direction = 8;
            }

        } else { // changing y
            randValue -= 7;
            if (randValue >= currentPosition.getY()) {
                steps = (randValue + 1 - currentPosition.getY()) * Panel.SQUARE_SIZE;
                direction = 6;
            } else {
                steps = (randValue + 1) * Panel.SQUARE_SIZE;
                direction = 2;
            }
        }
    }

    private void moveDiagonally(Position currentPosition, Random random) {
        int bound = currentPosition.getDistanceToWall() * 2 + 5;
        int id = random.nextInt(bound) + 1;

        int freeSpaces = Math.min(currentPosition.getX(), currentPosition.getY()); // Top left
        if (id <= freeSpaces) {
            direction = 1;
            steps = id * Panel.SQUARE_SIZE;
            return;
        } else {
            id -= freeSpaces;
        }

        freeSpaces = Math.min(7 - currentPosition.getX(), currentPosition.getY()); // Top right
        if (id <= freeSpaces) {
            direction = 3;
            steps = id * Panel.SQUARE_SIZE;
            return;
        } else {
            id -= freeSpaces;
        }

        freeSpaces = Math.min(7 - currentPosition.getX(), 7 - currentPosition.getY()); // Down right
        if (id <= freeSpaces) {
            direction = 5;
            steps = id * Panel.SQUARE_SIZE;
            return;
        } else {
            id -= freeSpaces;
        }
        // Down left
        direction = 7;
        steps = id * Panel.SQUARE_SIZE;
    }

    private void moveOnce(Position currentPosition, Random random) {
        ArrayList<Integer> possibleDirections = new ArrayList<>();

        possibleDirections.add(1);
        possibleDirections.add(2);
        possibleDirections.add(3);
        possibleDirections.add(4);
        possibleDirections.add(5);
        possibleDirections.add(6);
        possibleDirections.add(7);
        possibleDirections.add(8);

        if (currentPosition.getX() == 0) {
            possibleDirections.remove((Object) 1);
            possibleDirections.remove((Object) 7);
            possibleDirections.remove((Object) 8);
        }
        if (currentPosition.getX() == 7) {
            possibleDirections.remove((Object) 3);
            possibleDirections.remove((Object) 4);
            possibleDirections.remove((Object) 5);
        }
        if (currentPosition.getY() == 0) {
            possibleDirections.remove((Object) 1);
            possibleDirections.remove((Object) 2);
            possibleDirections.remove((Object) 3);
        }
        if (currentPosition.getY() == 7) {
            possibleDirections.remove((Object) 5);
            possibleDirections.remove((Object) 6);
            possibleDirections.remove((Object) 7);
        }

        direction = possibleDirections.get(random.nextInt(possibleDirections.size()));
        steps = Panel.SQUARE_SIZE;
    }

}
