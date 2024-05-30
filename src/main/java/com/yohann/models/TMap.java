package com.yohann.models;

import com.yohann.enums.Type;

import java.util.ArrayList;
import java.util.List;

public class TMap {
    List<List<Case>> map;

    public TMap(String length, String height) {
        this(Integer.parseInt(length), Integer.parseInt(height));
    }

    public TMap(int length, int height) {
        this.map = new ArrayList<>();

        for(int x = 0; x < length; x++) {
            this.map.add(new ArrayList<>());
            for(int y = 0; y < height; y++) {
                this.map.get(x).add(new Case(Type.PLAINE, x, y));
            }
        }
    }

    public Case getAtCoordinates(int x, int y) {
        return this.map.get(x).get(y);
    }

    public void setAtCoordinates(Case object) {
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
