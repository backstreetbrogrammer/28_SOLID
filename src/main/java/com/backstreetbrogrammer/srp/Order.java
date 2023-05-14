package com.backstreetbrogrammer.srp;

public class Order {

    private int orderId;
    private String symbol;
    private Double price;
    private Integer quantity;
    private String side;
    private boolean isDMA;

    //constructor, getters and setters

    public String getSide() {
        return side;
    }

    public boolean isDMA() {
        return isDMA;
    }
}
