package com.skypay.bankkata;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountAcceptanceTest {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Test
    void should_print_statement_with_transactions() {
        // Given
        Account account = new Account();
        account.deposit(1000);
        account.withdraw(500);
        account.deposit(2000);

        // When
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        account.printStatement();

        System.setOut(System.err);

        List<Transaction> transactions = account.getTransactions();
        StringBuilder expectedStatement = new StringBuilder("Date       | Amount | Balance\n");


        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction t = transactions.get(i);
            expectedStatement.append(t.getDate().format(formatter))
                    .append(" | ")
                    .append(String.format("%-6d", t.getAmount()))
                    .append(" | ")
                    .append(String.format("%-6d", t.getBalance()))
                    .append("\n");
        }

        String actualStatement = outputStream.toString();

        String normalizedExpected = normalizeString(expectedStatement.toString());
        String normalizedActual = normalizeString(actualStatement);

        System.out.println("Expected Statement:");
        System.out.println(normalizedExpected);

        System.out.println("Actual Statement:");
        System.out.println(normalizedActual);

        // Then
        assertEquals(normalizedExpected, normalizedActual);
    }

    // Méthode pour normaliser les chaînes
    private String normalizeString(String input) {
        return input.replace("\r\n", "\n")
                .replace("\r", "\n")
                .trim()
                .replaceAll("\\s+", " ");
    }
    @Test
    void should_print_empty_statement_for_new_account() {
        // Given
        Account account = new Account();

        // When
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        account.printStatement();

        System.setOut(System.err);

        // Then
        assertEquals("Date       | Amount | Balance", outputStream.toString().trim());
    }


    @Test
    void should_handle_deposit_and_withdrawal_correctly() {
        Account account = new Account();
        account.deposit(1000);
        account.withdraw(500);
        assertEquals(500, account.getCurrentBalance());
    }

    @Test
    void should_not_allow_withdrawals_with_insufficient_funds() {
        Account account = new Account();
        account.deposit(100);
        try {
            account.withdraw(200);
        } catch (IllegalArgumentException e) {
            assertEquals("Fonds insuffisants.", e.getMessage());
        }
        assertEquals(100, account.getCurrentBalance());
    }

    @Test
    void should_not_allow_negative_deposit() {
        Account account = new Account();
        try {
            account.deposit(-100);
        } catch (IllegalArgumentException e) {
            assertEquals("Le montant du dépôt doit être positif.", e.getMessage());
        }
        assertEquals(0, account.getCurrentBalance());
    }

    @Test
    void should_not_allow_negative_withdraw() {
        Account account = new Account();
        try {
            account.withdraw(-100);
        } catch (IllegalArgumentException e) {
            assertEquals("Le montant du retrait doit être positif.", e.getMessage());
        }
        assertEquals(0, account.getCurrentBalance());
    }
}