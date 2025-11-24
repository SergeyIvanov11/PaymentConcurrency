package dto;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
    private long id;
    private long amount;
    private String currency;
    private Lock lock;

    public Account(long id, long amount, String currency) {
        this.id = id;
        this.amount = amount;
        this.currency = currency;
        this.lock = new ReentrantLock();
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
        return amount;
    }

    public void add(long value) {
        amount += value;
    }

    public void subtract(long value) {
        amount -= value;
        /*
        while(true) {
            long oldAmount = amount.get();
            if(oldAmount < value) return false;
            long newAmount = oldAmount - value;
            if(amount.compareAndSet(oldAmount, newAmount)) return true;
        }
         */
    }

    public Lock getLock() {
        return lock;
    }
}
