package com.yohann.models;

import com.yohann.enums.Orientation;
import com.yohann.enums.Type;

import java.util.Objects;
import java.util.logging.Logger;

public class Adventurer extends Case {
    private final Logger logger = Logger.getLogger("Adventurer");
    private final String name;
    private final String moveset;
    private Orientation orientation;
    private int numberOfTreasures = 0;
    private char currentMovement;

    public Adventurer(String name, int x, int y, Orientation orientation, String moveset) {
        super(Type.AVENTURIER, x, y);
        this.name = name;
        this.moveset = moveset;
        this.orientation = orientation;
        this.currentMovement = moveset.charAt(0);
    }

    public Adventurer(String name, String x, String y, String orientation, String moveset) {
        this(name, Integer.parseInt(x), Integer.parseInt(y), Orientation.retrieveByLetter(orientation), moveset);
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
        logger.info("Turning right");
        int newOrdinal = this.orientation.ordinal() + 1 == 4
                ? 0
                : this.orientation.ordinal() + 1;
        this.orientation = Orientation.values()[newOrdinal];
    }

    public void turnLeft() {
        logger.info("Turning left");
        int newOrdinal = this.orientation.ordinal() == 0
                ? 3
                : this.orientation.ordinal() - 1;
        this.orientation = Orientation.values()[newOrdinal];
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

    public String getName() {
        return name;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public String getMoveset() {
        return moveset;
    }
}
