package com.yohann.models;

import com.yohann.enums.Type;

import java.util.Objects;

public class Treasure extends Case {
    private int quantity;

    public Treasure(int x, int y, int quantity) {
        super(Type.TRESOR, x, y);
        this.quantity = quantity;
    }

    public Treasure(String x, String y, String quantity) {
        super(Type.TRESOR, x, y);
        this.quantity = Integer.parseInt(quantity);
    }

    public void takeOne() {
        //TODO: check le 0 trésors
        this.quantity -= 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Treasure)) return false;
        if (!super.equals(o)) return false;
        Treasure treasure = (Treasure) o;
        return quantity == treasure.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), quantity);
    }

    public int getQuantity() {
        return quantity;
    }
}
