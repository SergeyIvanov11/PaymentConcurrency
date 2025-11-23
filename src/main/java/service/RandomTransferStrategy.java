package main.java.service;

import main.java.dto.Account;

import java.util.List;
import java.util.Random;

public class RandomTransferStrategy implements TransferStrategy {
    @Override
    public TransferRequest createTransfer(List<Account> accounts) {
        Random random = new Random();
        int fromIndex = 0;
        int toIndex = 0;
        while (fromIndex == toIndex) {
            fromIndex = random.nextInt(0, accounts.size());
            toIndex = random.nextInt(0, accounts.size());
        }
        Account from = accounts.get(fromIndex);
        Account to = accounts.get(toIndex);
        long amount = random.nextLong(100, 10_000);
        return new TransferRequest(from, to, amount);
    }
}