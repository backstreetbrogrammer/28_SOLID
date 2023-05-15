package com.backstreetbrogrammer.liskov.before;

public class Rectangle {

    private int width;
    private int height;

    public Rectangle(final int width, final int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(final int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(final int height) {
        this.height = height;
    }

    public int computeArea() {
        return width * height;
    }
}
