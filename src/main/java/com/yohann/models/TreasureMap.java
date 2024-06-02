package com.yohann.models;

import com.yohann.enums.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class TreasureMap {
    private Logger logger = Logger.getLogger("TreasureMap");
    private List<List<Case>> map;
    private List<Adventurer> adventurers = new ArrayList<>();
    private final int length;
    private final int height;

    public TreasureMap(String length, String height) {
        this(Integer.parseInt(length), Integer.parseInt(height));
    }

    public TreasureMap(int length, int height) throws IllegalArgumentException {
        //TODO: Peut etre forcer la map à etre 2x2 minimum ?
        if(length == 0 || height == 0) throw new IllegalArgumentException("Map can't be of size 0");
        this.map = new ArrayList<>();
        this.length = length;
        this.height = height;

        for(int x = 0; x < length; x++) {
            this.map.add(new ArrayList<>());
            for(int y = 0; y < height; y++) {
                this.map.get(x).add(new Case(Type.PLAINE, x, y));
            }
        }
    }

    public boolean addAdventurer(Adventurer adventurer) {
        Adventurer found = this.adventurers.stream().filter(av -> av.getX() == adventurer.getX() && av.getY() == adventurer.getY()).findFirst().orElse(null);
        if(found != null) {
            logger.info("Adventurer already on these coordinates, current adventurer removed");
            return false;
        }
        this.adventurers.add(adventurer);
        return true;
    }

    public List<Adventurer> getAdventurers() {
        return this.adventurers;
    }

    public Case getAt(int x, int y) throws IndexOutOfBoundsException {
        if(
                x == -1
                || y == -1
                || x >= this.length
                || y >= this.height
        ) {
            throw new IndexOutOfBoundsException();
        }

        Adventurer adventurer = this.adventurers
                .stream()
                .filter(av -> av.getX() == x && av.getY() == y)
                .findFirst()
                .orElse(null);
        return adventurer == null ? this.map.get(x).get(y) : adventurer;
    }

    public boolean addOnMap(Case object) {
        try {
            Case found = getAt(object.getX(), object.getY());
            if(
                    !(found.getType() == Type.AVENTURIER && object.getType() == Type.TRESOR)
                    && !(found.getType() == Type.TRESOR && object.getType() == Type.AVENTURIER)
                    && found.getType() != Type.PLAINE
            ) {
                return false;
            }

            if(object.getType() == Type.AVENTURIER) {
                return this.addAdventurer((Adventurer) object);
            }

            this.map.get(object.getX()).set(object.getY(), object);
            return true;
        } catch (IndexOutOfBoundsException e) {
            logger.info("Position hors de la carte");
            return false;
        }
    }

    public Case getUnderAdventurer(Adventurer adventurer) {
        return map.get(adventurer.getX()).get(adventurer.getY());
    }

    public List<List<Case>> getMap() {
        return this.map;
    }

    public int getLength() {
        return this.length;
    }

    public int getHeight() {
        return this.height;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TreasureMap)) return false;
        TreasureMap that = (TreasureMap) o;
        return length == that.length && height == that.height && Objects.equals(map, that.map) && Objects.equals(adventurers, that.adventurers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logger, map, adventurers, length, height);
    }
}
