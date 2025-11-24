package service;

import dto.Account;

import java.util.List;
import java.util.concurrent.locks.Lock;

public class Operationist implements Runnable {

    List<Account> accounts;
    int сount;
    final TransferStrategy strategy;

    public Operationist(List<Account> accounts, int сount, TransferStrategy strategy) {
        this.accounts = accounts;
        this.сount = сount;
        this.strategy = strategy;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " начал работу");
        for (int i = 0; i < сount; i++) {
            TransferRequest request = strategy.createTransfer(accounts);
            transfer(request);
        }
        System.out.println(Thread.currentThread().getName() + " завершил работу");
    }

    private boolean transfer(TransferRequest request) {
        Account from = request.from;
        Account to = request.to;
        long amount = request.amount;

        if (!from.getCurrency().equals(to.getCurrency())) {
            System.out.println("Валюты не совпадают у аккаунтов: "
                    + from.getId() + " и " + to.getId());
            return false;
        }

        for (int i = 0; i < 3; i++) { // попытки захватить локи
            if (from.getLock().tryLock()) {
                try {
                    if (to.getLock().tryLock()) {
                        try {
                            if (from.getAmount() < amount) {
                                System.out.println("Недостаточно средств " +
                                        "для перевода с аккаунта " + from.getId());
                                return false;
                            }
                            from.subtract(amount);
                            to.add(amount);
                            System.out.println(from.getId() + " перевел "
                                    + to.getId() + " " + amount
                                    + " " + from.getCurrency());

                            return true;
                        } finally {
                            to.getLock().unlock();
                        }
                    }
                } finally {
                    from.getLock().unlock();
                }
            }
            Thread.yield(); // уступить время другим потокам
        }

        System.out.println("Не удалось захватить локи, пропуск операции");
        return false;
    }
}
