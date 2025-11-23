package main.java.service;

import main.java.dto.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TransferService {

    public void runTransfers(List<Account> accounts, int count, int operations, TransferStrategy strategy) {
        System.out.println("В начале сумма средств на всех аккаунтах была: " + sumAccounts(accounts));

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Operationist worker = new Operationist(accounts, operations, strategy);
            Thread t = new Thread(worker, "operationist-" + i);
            threads.add(t);
            t.start();
        }
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        /*
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < count; i++) {
            Operationist worker = new Operationist(accounts, operations);
            service.submit(worker, "operationist-" + i));
        }
        service.awaitTermination(10, TimeUnit.SECONDS);
        */

        System.out.println("Все операции завершены");
        System.out.println("После всех операций сумма средств на всех аккаунтах составляет: " + sumAccounts(accounts));
    }

    public String sumAccounts(List<Account> accounts){
        if (accounts.isEmpty()) {
            return "Счета отсутствуют";
        }

        String currency = accounts.get(0).getCurrency();
        long sum = 0;

        for (Account acc : accounts) {
            sum += acc.getAmount();
        }

        return sum + " " + currency;
    }
}
