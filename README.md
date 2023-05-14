# SOLID Design Principles

> This is a Java course to explain SOLID design principles.

Tools used:

- JDK 11
- Maven
- JUnit 5, Mockito
- IntelliJ IDE

## Table of contents

1. [SOLID Introduction](https://github.com/backstreetbrogrammer/28_SOLID#chapter-01-solid-introduction)
2. [Single Responsibility Principle](https://github.com/backstreetbrogrammer/28_SOLID#chapter-02-single-responsibility-principle)
3. [Open-Closed Principle](https://github.com/backstreetbrogrammer/28_SOLID#chapter-03-open-closed-principle)
4. Liskov Substitution Principle
5. Interface Segregation Principle
6. Dependency Inversion Principle

---

### Chapter 01. SOLID Introduction

The word **SOLID** is an acronym for:

- **S**: Single Responsibility Principle
- **O**: Open-Closed Principle
- **L**: Liskov Substitution Principle
- **I**: Interface Segregation Principle
- **D**: Dependency Inversion Principle

SOLID design principles encourage us to create more **maintainable**, **understandable**, and **flexible** software.

Consequently, as our applications grow in size, we can reduce their complexity and save ourselves a lot of headaches
further down the road!

---

### Chapter 02. Single Responsibility Principle

A class should ONLY have one responsibility. Furthermore, it should only have one reason to change.

Benefits:

- **Testing** – A class with one responsibility will have fewer test cases
- **Lower coupling** – Less functionality in a single class will have fewer dependencies
- **Organization** – Smaller, well-organized classes are easier to search than monolithic ones

Let's take an example.

```java
public class Order {

    private int orderId;
    private String symbol;
    private Double price;
    private Integer quantity;
    private String side;
    private boolean isDMA;

    //constructor, getters and setters
}
```

We have an `Order` class which represents client trading orders. And an `OrderProcessor` class as below:

```java
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
```

In `OrderProcessor` class, we can see there are many reasons for this class to change.

- If client wallet check logic changes, we need to change the class
- If order validation rules changes, we need to change the class
- If sending order to market-gateway or algo-engine routing changes, we need to change the class
- Even if printing the order fields change, we need to change the class

This definitely breaks the SRP principle.

Fixed class:

```java
public class OrderProcessor {

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
```

After fixing the class applying SRP, we have encapsulated each responsibility to separate classes which is much more
maintainable, flexible and readable.

---

### Chapter 03. Open-Closed Principle

Classes should be open for extension but closed for modification.

In doing so, we stop ourselves from modifying existing code and causing potential new bugs in an otherwise happy
application.

One exception to the rule is when fixing bugs in existing code.

Let's take the same example class `Order`, which has been working fine so far in our order management system.

```java
public class Order {

    private int orderId;
    private String symbol;
    private Double price;
    private Integer quantity;
    private String side;
    private boolean isDMA;

    //constructor, getters and setters
}
```

Now suppose we started specific order features required for DMA (direct market access) or Algo order (VWAP, TWAP, etc.).

It might be tempting to just open up the `Order` class and add fields pertaining to DMA or Algo orders — but this may
cause errors and might throw up in our application which was already working fine.

So, to fix this and applying open-close principle => we should use inheritance.

```java
public class DMAOrder extends Order {
    // DMA fields
}
```

```java
public class AlgoOrder extends Order {
    // Algo fields
}
```

Thus, we can say that `Order` class is now open for extension (by inheritance) and closed for modification.

---

### Chapter 04. Liskov Substitution Principle