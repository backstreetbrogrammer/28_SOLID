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
4. [Liskov Substitution Principle](https://github.com/backstreetbrogrammer/28_SOLID#chapter-04-liskov-substitution-principle)
5. [Interface Segregation Principle](https://github.com/backstreetbrogrammer/28_SOLID#chapter-05-interface-segregation-principle)
6. [Dependency Inversion Principle](https://github.com/backstreetbrogrammer/28_SOLID#chapter-06-dependency-inversion-principle)
7. Summary

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

This principle states that "Derived or child classes must be substitutable for their base or parent classes".

In other words, if class A is a subtype of class B, then we should be able to replace B with A without interrupting the
behavior of our program.

```
class B {}

class A extends B {}

public void useClass(B object) {}

useClass(new B());

// as per Liskov Substitution Principle, we can pass instance of A object as argument without any error.
useClass(new A());
```

Let's demonstrate the same using actual classes: we have a `Rectangle` class:

```java
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
```

Now, let's create another child class `Square` as:

```java
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
```

Suppose we have a method that takes `Rectangle` class object as argument:

```
void useRectangle(Rectangle rectangle) {
		rectangle.setHeight(20);
		rectangle.setWidth(30);
		assert rectangle.getHeight() == 20 : "Height Not equal to 20";
		assert rectangle.getWidth() == 30 : "Width Not equal to 30";
}
```

And, I call this method with Rectangle object and Square object:

```
Rectangle rectangle = new Rectangle(10, 20);
useRectangle(rectangle);

Square square = new Square(10);
useRectangle(square); 
```

This call `useRectangle(square)` will fail because when we call `rectangle.setWidth(30)`, it will set `Square` side
as **30** and this assertion will fail:

```
assert rectangle.getHeight() == 20 : "Height Not equal to 20";
```

**Fixing** the class structure, which should adhere to **Liskov Substitution Principle**

```java
public interface Shape {
    int computeArea();
}
```

Now, `Rectangle` class and `Square` class can both implement `Shape` interface.

```java
public class Rectangle implements Shape {
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

    @Override
    public int computeArea() {
        return width * height;
    }
}
```

```java
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
```

For the method for `computeArea()`, we can create as such:

```
void computeAreaForAnyShape(Shape shape) {
	shape.computeArea();
}
```

And, we can pass both `Rectangle` and `Shape` class objects without any error.

```
Rectangle rectangle = new Rectangle(10, 20);
computeAreaForAnyShape(rectangle); // 10 * 20

Square square = new Square(10);
computeAreaForAnyShape(square); // 10 * 10
```

---

### Chapter 05. Interface Segregation Principle

Larger interfaces should be split into smaller ones. By doing so, we can ensure that implementing classes only need to
be concerned about the methods that are of interest to them.

Suppose we have a fat interface:

```java
interface Account {
    double getBalance();

    void processLocalPayment(double amount);

    void processInternationalPayment(double amount);
}
```

Now suppose there is a class which only needs to take care of maintaining the balance and not bothered about payments,
it is forced to implement payment methods with empty implementation or throw some exception.

```java
public class MaintainAccountBalance implements Account {
    private double balance;

    public double withdraw(final double amount) {
        final double currentBalance = getBalance();
        if (amount > currentBalance) {
            throw new RuntimeException("Not enough balance");
        }
        setBalance(currentBalance - amount);
        return amount;
    }

    private void setBalance(final double amount) {
        balance = amount;
    }

    @Override
    public double getBalance() {
        // check from persistence or other logic
        return balance;
    }

    @Override
    public void processLocalPayment(final double amount) {
        // not concerned - may throw UnsupportedOperationException
    }

    @Override
    public void processInternationalPayment(final double amount) {
        // not concerned - may throw UnsupportedOperationException
    }
}
```

This is breaking Interface Segregation Principle.

Fixing is just to break the `Account` interface into 3 smaller interfaces.

```java
public interface BaseAccount {
    double getBalance();
}
```

```java
public interface LocalMoneyTransfer {
    void processLocalPayment(double amount);
}
```

```java
public interface InternationalMoneyTransfer {
    void processInternationalPayment(double amount);
}
```

Now we can separate classes dedicated to implementation of each above functions creating cleaner code and highly
cohesive classes. It also helps in Single Responsibility Principle for a class.

---

### Chapter 06. Dependency Inversion Principle

---

### Summary

> Single Responsibility Principle

Each class should be responsible for a single part or functionality of the system.

> Open-Closed Principle

Software components should be open for extension, but closed for modification.

> Liskov Substitution Principle

Objects of a superclass should be replaceable with objects of its subclasses without breaking the system.

> Interface Segregation Principle

No client should be forced to depend on methods that it does not use.

> Dependency Inversion Principle

High-level modules should not depend on low-level modules. Both should depend on abstractions.