package test.java;

import main.java.dto.Account;
import main.java.service.Operationist;
import main.java.service.TransferRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OperationistTest {
    @Test
    void transferShouldMoveMoneyBetweenAccounts() {
        Account a = new Account(1, 1000, "RUB");
        Account b = new Account(2, 500, "RUB");

        Operationist op = new Operationist(null, 0, null);
        boolean ok = op.transfer(new TransferRequest(a, b, 300));

        assertTrue(ok);
        assertEquals(700, a.getAmount());
        assertEquals(800, b.getAmount());
    }

    @Test
    void transferShouldFailIfDifferentCurrencies() {
        Account a = new Account(1, 1000, "RUB");
        Account b = new Account(2, 500, "USD");

        Operationist op = new Operationist(null, 0, null);
        boolean ok = op.transfer(new TransferRequest(a, b, 300));

        assertFalse(ok);
        assertEquals(1000, a.getAmount());
        assertEquals(500, b.getAmount());
    }

    @Test
    void transferShouldFailIfInsufficientFunds() {
        Account a = new Account(1, 200, "RUB");
        Account b = new Account(2, 500, "RUB");

        Operationist op = new Operationist(null, 0, null);
        boolean ok = op.transfer(new TransferRequest(a, b, 300));

        assertFalse(ok);
        assertEquals(200, a.getAmount());
        assertEquals(500, b.getAmount());
    }
}