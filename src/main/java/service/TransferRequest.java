package main.java.service;

import main.java.dto.Account;

public class TransferRequest {
    Account from;
    Account to;
    long amount;

    public TransferRequest(Account from, Account to, long amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public Account getFrom() {
        return from;
    }

    public long getAmount() {
        return amount;
    }

    public Account getTo() {
        return to;
    }
}
