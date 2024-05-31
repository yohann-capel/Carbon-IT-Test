package com.yohann.models;

import com.yohann.enums.Type;

import java.util.ArrayList;
import java.util.List;

public class TreasureMap {
    List<List<Case>> map;

    public TreasureMap(String length, String height) {
        this(Integer.parseInt(length), Integer.parseInt(height));
    }

    public TreasureMap(int length, int height) throws IllegalArgumentException {
        if(length == 0 && height == 0) throw new IllegalArgumentException("Map can't be of size 0");
        this.map = new ArrayList<>();

        for(int x = 0; x < length; x++) {
            this.map.add(new ArrayList<>());
            for(int y = 0; y < height; y++) {
                this.map.get(x).add(new Case(Type.PLAINE, x, y));
            }
        }
    }

    public Case get(int x, int y) {
        return this.map.get(x).get(y);
    }

    public void addOnMap(Case object) {
        this.map.get(object.getX()).set(object.getY(), object);
    }

    public List<List<Case>> getMap() {
        return this.map;
    }

    public int getLength() {
        return this.map.size();
    }

    public int getHeight() {
        return this.map.get(0).size();
    }
}
