import dto.Account;
import service.*;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        List<Account> accounts = List.of(
                new Account(1, 30000, "RUB"),
                new Account(2, 40000, "RUB"),
                new Account(3, 30500, "RUB")
        );

        TransferService service = new TransferService();

        TransferStrategy strategy = new RandomTransferStrategy();
   //     TransferStrategy strategy = new SequentialTransferStrategy(100);

        Instant begin = Instant.now();

        service.runTransfers(accounts,5, 40, strategy);

        Instant end = Instant.now();
        Duration duration = Duration.between(begin, end);

        System.out.println("Времени заняло: " + duration.toMillis() + " мс");

/*
        Instant begin2 = Instant.now();
        HappensBeforeDoesntWork checker = new HappensBeforeDoesntWork();
        checker.checkHappensBefore(1_000_000L);
        Instant end2 = Instant.now();
        Duration duration2 = Duration.between(begin2, end2);
        System.out.println("Времени заняло: " + + duration2.toMillis() + " мс");
*/
    }
}