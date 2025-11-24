import dto.Account;
import org.junit.jupiter.api.BeforeEach;
import service.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OperationistTest {
    TransferStrategy strategy;

    @BeforeEach
    void before() {
        strategy = new SequentialTransferStrategy(100);
    }

    @Test
    void transferShouldMoveMoneyBetweenAccounts() {
        Account a = new Account(1, 1000, "RUB");
        Account b = new Account(2, 500, "RUB");

        Operationist op = new Operationist(List.of(a, b), 1, strategy);
        op.run();

        assertEquals(900, a.getAmount());
        assertEquals(600, b.getAmount());
    }

    @Test
    void transferShouldFailIfDifferentCurrencies() {
        Account a = new Account(1, 1000, "RUB");
        Account b = new Account(2, 500, "USD");

        Operationist op = new Operationist(List.of(a, b), 1, strategy);
        op.run();

        assertEquals(1000, a.getAmount());
        assertEquals(500, b.getAmount());
    }

    @Test
    void transferShouldFailIfInsufficientFunds() {
        Account a = new Account(1, 50, "RUB");
        Account b = new Account(2, 500, "RUB");

        Operationist op = new Operationist(List.of(a, b), 1, strategy);
        op.run();

        assertEquals(50, a.getAmount());
        assertEquals(500, b.getAmount());
    }
}