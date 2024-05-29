package com.yohann.enums;

// Valeurs en français pour matcher l'exercice
public enum Type {
    PLAINE("P"),
    MONTAGNE("M"),
    TRESOR("T"),
    AVENTURIER("A");

    private String type;

    Type(String c) {
    }

    public String getType() {
        return this.type;
    }

    public static Type retrieveByLetter(String letter) {
        //Switch car seulement 4 types
        switch (letter) {
            case "P": return PLAINE;
            case "M": return MONTAGNE;
            case "T": return TRESOR;
            case "A": return AVENTURIER;
            default: throw new IllegalArgumentException(letter);
        }
    }
}
