package com.backstreetbrogrammer.liskov.before;

public class Square extends Rectangle {
    public Square(final int side) {
        super(side, side);
    }

    @Override
    public void setWidth(final int width) {
        setSide(width);
    }

    @Override
    public void setHeight(final int height) {
        setSide(height);
    }

    public void setSide(final int side) {
        super.setWidth(side);
        super.setHeight(side);
    }

}
