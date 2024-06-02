package com.yohann.models;

import com.yohann.enums.Move;
import com.yohann.enums.Orientation;
import com.yohann.enums.Type;
import com.yohann.exceptions.WrongMoveException;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Adventurer extends Case {
    private final Logger logger = Logger.getLogger("Adventurer");
    private final String name;
    private final List<Move> moveset;
    private Orientation orientation;
    private int numberOfTreasures = 0;
    private Move currentMove;

    public Adventurer(String name, int x, int y, Orientation orientation, String moveset) {
        super(Type.AVENTURIER, x, y);
        this.name = name;
        this.moveset = Arrays.stream(moveset.toUpperCase().split("")).map(Move::retrieveByLetter).collect(Collectors.toList());
        this.orientation = orientation;
        this.updateNextMove();
    }

    public Adventurer(String name, String x, String y, String orientation, String moveset) {
        this(name, Integer.parseInt(x), Integer.parseInt(y), Orientation.retrieveByLetter(orientation), moveset);
    }

    public boolean doCurrentMove(TreasureMap map) {
        boolean moveResult = true;
        switch (this.currentMove) {
            case AVANCER:
                moveResult = this.moveForward(map);
                break;
            case DROITE:
                this.turnRight();
                break;
            case GAUCHE:
                this.turnLeft();
                break;
            default:
                throw new IllegalArgumentException();
        }

        this.updateNextMove();
        return moveResult;
    }

    private void updateNextMove() {
        if(this.moveset.isEmpty()) {
            this.currentMove = null;
            return;
        }
        
        this.currentMove = this.moveset.get(0);
        this.moveset.remove(0);
    }

    public Move getCurrentMove() {
        return this.currentMove;
    }

    public boolean pickUp(Treasure treasure) {
        logger.info("Picking up treasure !");

        if (treasure.getX() != this.x || treasure.getY() != this.y) {
            logger.info("Your arm is not that long !");
            return false;
        }

        if(!treasure.hasTreasureLeft()) {
            logger.info("There is nothing left !");
            return false;
        }

        treasure.takeOne();
        this.numberOfTreasures++;
        logger.info("Treasure collected !");
        return true;
    }

    public void turnRight() {
        if(this.currentMove != Move.DROITE)
            throw new WrongMoveException("Current move needs to be DROITE to turn right");

        logger.info("Turning right");
        int newOrdinal = this.orientation.ordinal() + 1 == 4
                ? 0
                : this.orientation.ordinal() + 1;
        this.orientation = Orientation.values()[newOrdinal];
    }

    public void turnLeft() {
        if(this.currentMove != Move.GAUCHE)
            throw new WrongMoveException("Current move needs to be GAUCHE to turn left");

        logger.info("Turning left");
        int newOrdinal = this.orientation.ordinal() == 0
                ? 3
                : this.orientation.ordinal() - 1;
        this.orientation = Orientation.values()[newOrdinal];
    }

    public boolean moveForward(TreasureMap map) {
        if(this.currentMove != Move.AVANCER)
            throw new WrongMoveException("Current move needs to be AVANCER to go forward");

        Point next = calculateNextCoordinate();
        try {
            Case futurePos = map.getAt(this.x + next.x, this.y + next.y);
            if(futurePos.getType() == Type.MONTAGNE || futurePos.getType() == Type.AVENTURIER) {
                return false;
            }

            this.x = futurePos.x;
            this.y = futurePos.y;
            return true;
        } catch (IndexOutOfBoundsException e) {
            logger.info("Coordinates are out of the map");
        }

        return false;
    }

    public Point calculateNextCoordinate() {
        switch (this.orientation) {
            case NORD:
                return new Point(0, -1);
            case EST:
                return new Point(1, 0);
            case SUD:
                return new Point(0, 1);
            case OUEST:
                return new Point(-1, 0);
            default:
                throw new IllegalArgumentException("Orientation doesn't exists");

        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Adventurer)) return false;
        if (!super.equals(o)) return false;
        Adventurer that = (Adventurer) o;
        return numberOfTreasures == that.numberOfTreasures && Objects.equals(name, that.name) && orientation == that.orientation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, numberOfTreasures, x, y, orientation);
    }

    @Override
    public String toString() {
        return String.format(
                "%s - %s - %s - %s - %s - %s",
                this.type.getType(),
                this.name,
                this.x,
                this.y,
                this.orientation.getOrientation(),
                this.numberOfTreasures
        );
    }

    public Orientation getOrientation() {
        return this.orientation;
    }

    public int getNumberOfTreasures() {
        return this.numberOfTreasures;
    }

    public String getName() {
        return this.name;
    }
}
