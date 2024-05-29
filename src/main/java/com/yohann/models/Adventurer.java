package com.yohann.models;

import com.yohann.enums.Orientation;
import com.yohann.enums.Type;

import java.util.Objects;

public class Adventurer extends Case {
    private final String name;
    private final String moveSet;
    private final Orientation orientation;

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
        return Objects.equals(name, that.name) && Objects.equals(moveSet, that.moveSet) && orientation == that.orientation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, moveSet, orientation);
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
