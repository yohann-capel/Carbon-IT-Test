package com.yohann.models;

import com.yohann.enums.Orientation;
import com.yohann.enums.Type;

import java.util.Objects;

public class Adventurer extends Case {
    private final String name;
    private final String moveSet;
    private final Orientation orientation;
    private int numberOfTreasures = 0;

    public Adventurer(String name, int x, int y, Orientation orientation, String moveSet) {
        super(Type.AVENTURIER, x, y);
        this.name = name;
        this.moveSet = moveSet;
        this.orientation = orientation;
    }

    public Adventurer(String name, String x, String y, String orientation, String moveSet) {
        super(Type.AVENTURIER, x, y);
        this.name = name;
        this.moveSet = moveSet;
        this.orientation = Orientation.retrieveByLetter(orientation);
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

    public String getMoveSet() {
        return moveSet;
    }
}
