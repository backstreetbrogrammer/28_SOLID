package com.backstreetbrogrammer.isp;

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
