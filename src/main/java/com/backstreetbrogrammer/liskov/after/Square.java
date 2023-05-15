package com.backstreetbrogrammer.liskov.after;

public class Square implements Shape {
    private int side;

    public Square(final int side) {
        this.side = side;
    }

    public void setSide(final int side) {
        this.side = side;
    }

    public int getSide() {
        return side;
    }

    @Override
    public int computeArea() {
        return side * side;
    }
}
