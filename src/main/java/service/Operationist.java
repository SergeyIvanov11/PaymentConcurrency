package main.java.service;

import main.java.dto.Account;

import java.util.List;

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

    public boolean transfer(TransferRequest request) {
        Account from = request.from;
        Account to = request.to;
        long amount = request.amount;

        if (!from.getCurrency().equals(to.getCurrency())) {
            System.out.println("Валюты не совпадают у аккаунтов: "
                    + from.getId() + " и " + to.getId());
            return false;
        }

        if (!from.subtract(amount)) {
            System.out.println("Недостаточно средств на счёте " + from.getId());
            return false;
        }
        to.add(amount);
        System.out.println(from.getId() + " перевел "
                + to.getId() + " " + amount
                + " " + from.getCurrency());
        return true;
    }
}
