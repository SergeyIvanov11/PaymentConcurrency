import dto.Account;
import service.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransferStrategyTest {
    @Test
    void strategyShouldGenerateValidRequest() {
        List<Account> accounts = List.of(
                new Account(1, 1000, "RUB"),
                new Account(2, 1000, "RUB"),
                new Account(3, 1000, "RUB")
        );

        TransferStrategy s = new RandomTransferStrategy();

        TransferRequest r = s.createTransfer(accounts);

        assertNotNull(r);
        assertNotSame(r.getFrom(), r.getTo());
        assertTrue(r.getAmount() > 0);
    }

    @Test
    void shouldCreateTransferToNextAccount() {
        List<Account> accounts = List.of(
                new Account(1, 1000, "RUB"),
                new Account(2, 1000, "RUB")
        );

        TransferStrategy s = new SequentialTransferStrategy(100);

        TransferRequest r = s.createTransfer(accounts);

        assertEquals(1, r.getFrom().getId());
        assertEquals(2, r.getTo().getId());
        assertEquals(100, r.getAmount());
    }
}