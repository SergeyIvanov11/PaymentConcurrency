package main.java.service;

import main.java.dto.Account;

import java.util.List;

public interface TransferStrategy {
    TransferRequest createTransfer(List<Account> accounts);
}
