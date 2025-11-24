package service;

import dto.Account;

import java.util.List;

public interface TransferStrategy {
    TransferRequest createTransfer(List<Account> accounts);
}
