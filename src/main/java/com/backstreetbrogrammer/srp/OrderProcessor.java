package com.backstreetbrogrammer.srp;

public class OrderProcessor {

    public void process(final Order order) {
        if (order.getSide().equals("BUY")) {
            checkClientWallet(order);
        }

        validateOrder(order);

        if (order.isDMA()) {
            sendOrderToMarketGateway(order);
        } else {
            sendOrderToAlgoEngine(order);
        }

        printOrder(order);
    }

    private void checkClientWallet(final Order order) {
        // check client wallet and throw exception if not sufficient funds
    }

    private void validateOrder(final Order order) {
        // throw exception if not valid order
    }

    private void sendOrderToMarketGateway(final Order order) {
        // send order to low-touch market gateway
    }

    private void sendOrderToAlgoEngine(final Order order) {
        // send order to internal algo engine or sor
    }

    private void printOrder(final Order order) {
        // print the order to common logger
    }

}
