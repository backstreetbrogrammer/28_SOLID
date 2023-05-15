package com.backstreetbrogrammer.isp;

public interface Account {
    double getBalance();

    void processLocalPayment(double amount);

    void processInternationalPayment(double amount);
}
