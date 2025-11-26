package service;

import dto.Account;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TransferService {

    public void runTransfers(List<Account> accounts, int count, int operations, TransferStrategy strategy) throws InterruptedException {
        System.out.println("В начале сумма средств на всех аккаунтах была: " + sumAccounts(accounts));

        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(count);

        for (int i = 0; i < count; i++) {
            service.submit(() -> {
                try {
                    new Operationist(accounts, operations, strategy).run();
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        service.shutdown();

        System.out.println("Все операции завершены");
        System.out.println("После всех операций сумма средств на всех аккаунтах составляет: " + sumAccounts(accounts));
    }

    public String sumAccounts(List<Account> accounts){
        if (accounts.isEmpty()) return "Счета отсутствуют";

        String currency = accounts.get(0).getCurrency();

        long sum = accounts.stream()
                .mapToLong(Account::getAmount)
                .sum();

        return sum + " " + currency;
    }
}
