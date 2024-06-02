package com.yohann.enums;

public enum Move {
    AVANCER("A"),
    DROITE("D"),
    GAUCHE("G");

    private final String move;

    Move(String move) {
        this.move = move;
    }

    public String getMove() {
        return this.move;
    }

    public static Move retrieveByLetter(String letter) {
        //Switch car seulement 4 types
        switch (letter) {
            case "A": return AVANCER;
            case "D": return DROITE;
            case "G": return GAUCHE;
            default: throw new IllegalArgumentException(letter);
        }
    }
}
