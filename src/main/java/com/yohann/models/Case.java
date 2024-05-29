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

    public Type getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
