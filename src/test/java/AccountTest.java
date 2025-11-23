package test.java;

import main.java.dto.Account;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    @Test
    void addShouldIncrease() {
        Account acc = new Account(1L, 1000L, "RUB");
        acc.add(500);
        assertEquals(1500L, acc.getAmount());
    }

    @Test
    void subtractShouldWork() {
        Account acc = new Account(1L, 1000L, "RUB");
        boolean ok = acc.subtract(300);
        assertTrue(ok);
        assertEquals(700L, acc.getAmount());
    }

    @Test
    void subtractShouldFail() {
        Account acc = new Account(1L, 200L, "RUB");
        boolean ok = acc.subtract(500);
        assertFalse(ok);
        assertEquals(200L, acc.getAmount());
    }

    @Test
    void subtractShouldBeAtomic() throws InterruptedException {
        Account acc = new Account(1L, 1_000_000L, "RUB");

        Runnable r = () -> {
            for (int i = 0; i < 100_000; i++) {
                acc.subtract(1);
            }
        };

        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        assertEquals(1_000_000 - 200_000, acc.getAmount());
    }
}