package com.yohann.models;

import com.yohann.enums.Type;

import java.util.Objects;

public class Case {
    protected final Type type;
    protected int x;
    protected int y;

    public Case(Type type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public Case(Type type, String x, String y) {
        this.type = type;
        this.x = Integer.parseInt(x);
        this.y = Integer.parseInt(y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Case)) return false;
        Case aCase = (Case) o;
        return x == aCase.x && y == aCase.y && type == aCase.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, x, y);
    }

    @Override
    public String toString() {
        return String.format("%s - %s - %s", this.type.getType(), this.x, this.y);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Type getType() {
        return this.type;
    }
}
