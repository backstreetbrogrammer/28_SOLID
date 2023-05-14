package com.backstreetbrogrammer.srp;

public class OrderProcessorSRP {

    private WalletCheckerService walletCheckerService;
    private OrderValidator orderValidator;
    private OrderRouter orderRouter;
    private OrderPrinter orderPrinter;

    public void process(final Order order) {
        if (order.getSide().equals("BUY")) {
            walletCheckerService.checkClientWallet(order);
        }

        orderValidator.validateOrder(order);

        if (order.isDMA()) {
            orderRouter.sendOrderToMarketGateway(order);
        } else {
            orderRouter.sendOrderToAlgoEngine(order);
        }

        orderPrinter.printOrder(order);
    }

    class WalletCheckerService {
        public void checkClientWallet(final Order order) {
            // check client wallet and throw exception if not sufficient funds
        }
    }

    class OrderValidator {
        public void validateOrder(final Order order) {
            // throw exception if not valid order
        }
    }

    class OrderRouter {
        public void sendOrderToMarketGateway(final Order order) {
            // send order to low-touch market gateway
        }

        public void sendOrderToAlgoEngine(final Order order) {
            // send order to internal algo engine or sor
        }
    }

    class OrderPrinter {
        public void printOrder(final Order order) {
            // print the order to common logger
        }
    }

}
