package com.skypay.bankkata;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Account implements AccountService {
    private final List<Transaction> transactions = new ArrayList<>();

    @Override
    public void deposit(int amount) {
        if (amount <= 0) throw new IllegalArgumentException("Le montant du dépôt doit être positif.");
        int balance = getCurrentBalance() + amount;
        transactions.add(new Transaction(LocalDate.now(), amount, balance));
    }

    @Override
    public void withdraw(int amount) {
        if (amount <= 0) throw new IllegalArgumentException("Le montant du retrait doit être positif.");
        if (amount > getCurrentBalance()) throw new IllegalArgumentException("Fonds insuffisants.");
        int balance = getCurrentBalance() - amount;
        transactions.add(new Transaction(LocalDate.now(), -amount, balance));
    }

    @Override
    public void printStatement() {
        StatementPrinter.print(transactions);
    }

    public int getCurrentBalance() {
        return transactions.isEmpty() ? 0 : transactions.get(transactions.size() - 1).getBalance();
    }
    public List<Transaction> getTransactions() {
        return transactions;
    }
}