package com.skypay.bankkata;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class StatementPrinter {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static void print(List<Transaction> transactions) {
        System.out.println("Date       | Amount | Balance");
        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction t = transactions.get(i);
            System.out.printf("%-10s | %-6d | %-6d%n", t.getDate().format(FORMATTER), t.getAmount(), t.getBalance());
        }
    }
}