package test.java;

import main.java.dto.Account;
import main.java.service.TransferRequest;
import main.java.service.TransferService;
import main.java.service.TransferStrategy;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransferServiceTest {
    @Test
    void runTransfersTest() {
        List<Account> accounts = Arrays.asList(
                new Account(1, 1000, "RUB"),
                new Account(2, 1000, "RUB"),
                new Account(3, 1000, "RUB")
        );

        TransferService service = new TransferService();
        String before = service.sumAccounts(accounts);

        // фиктивная стратегия — переводит 1
        TransferStrategy strategy = (accs) -> {
            Account from = accs.get(0);
            Account to   = accs.get(1);
            return new TransferRequest(from, to, 1);
        };

        service.runTransfers(accounts, 5, 10, strategy);

        String after = service.sumAccounts(accounts);

        assertEquals(before, after);
        assertEquals(accounts.get(0).getAmount(), 950);
        assertEquals(accounts.get(1).getAmount(), 1050);
    }
}