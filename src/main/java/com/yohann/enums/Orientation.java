package com.yohann.enums;

// Valeurs en français pour matcher l'exercice
public enum Orientation {
    NORD("N"),
    EST("E"),
    SUD("S"),
    OUEST("O");

    private final String orientation;

    Orientation(String orientation) {
        this.orientation = orientation;
    }

    public String getOrientation() {
        return this.orientation;
    }

    public static Orientation retrieveByLetter(String letter) {
        //Switch car seulement 4 types
        switch (letter) {
            case "N": return NORD;
            case "S": return SUD;
            case "E": return EST;
            case "O": return OUEST;
            default: throw new IllegalArgumentException(letter);
        }
    }
}
