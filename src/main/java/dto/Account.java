package main.java.dto;

import java.util.concurrent.atomic.AtomicLong;

public class Account {
    private long id;
    private AtomicLong amount;
    private String currency;

    public Account(long id, long amount, String currency) {
        this.id = id;
        this.amount = new AtomicLong(amount);
        this.currency = currency;
    }

    public long getId() {
        return id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public long getAmount() {
        return amount.get();
    }

    public void add(long value) {
        amount.addAndGet(value);
    }

    public boolean subtract(long value) {
        while(true) {
            long oldAmount = amount.get();
            if(oldAmount < value) return false;
            long newAmount = oldAmount - value;
            if(amount.compareAndSet(oldAmount, newAmount)) return true;
        }
    }
}
