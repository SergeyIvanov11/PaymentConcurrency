package main.java;

import main.java.*;
import main.java.dto.Account;
import main.java.service.RandomTransferStrategy;
import main.java.service.TransferService;
import main.java.service.TransferStrategy;

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
     //  TransferStrategy strategy = new SequentialTransferStrategy(100);

        long begin = System.nanoTime();

        service.runTransfers(accounts,5, 20, strategy);

        long end = System.nanoTime();
        System.out.println("Времени заняло: " + (end - begin));

/*
        long begin = System.nanoTime();
        HappensBeforeDoesntWork checker = new HappensBeforeDoesntWork();
        checker.checkHappensBefore(1_000_000L);
        long end = System.nanoTime();
        System.out.println("Времени заняло: " + (end - begin));

 */
    }
}