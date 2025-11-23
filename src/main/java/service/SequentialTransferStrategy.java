package main.java.service;

import main.java.dto.Account;

import java.util.List;
import java.util.Random;

public class SequentialTransferStrategy implements TransferStrategy {

    private final long fixedAmount;

    public SequentialTransferStrategy(long fixedAmount) {
        this.fixedAmount = fixedAmount;
    }

    @Override
    public TransferRequest createTransfer(List<Account> accounts) {
        Random random = new Random();
        int fromIndex = random.nextInt(0, accounts.size() - 1);
        Account from = accounts.get(fromIndex);
        Account to = accounts.get(fromIndex + 1);
        return new TransferRequest(from, to, fixedAmount);
    }
}
