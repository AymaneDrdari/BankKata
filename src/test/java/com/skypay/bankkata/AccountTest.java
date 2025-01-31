package com.skypay.bankkata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class AccountTest {

    private Account account;
    private StatementPrinter statementPrinter;

    @BeforeEach
    void setUp() {
        statementPrinter = mock(StatementPrinter.class);
        account = new Account();
    }

    @Test
    void deposit_shouldAddTransactionWithCorrectBalance() {
        account.deposit(1000);
        account.deposit(2000);
        assert(account.getCurrentBalance() == 3000);
    }

    @Test
    void withdraw_shouldSubtractAmountAndUpdateBalance() {
        account.deposit(2000);
        account.withdraw(500);
        assert(account.getCurrentBalance() == 1500);
    }

    @Test
    void withdraw_shouldThrowException_whenFundsAreInsufficient() {
        account.deposit(1000);
        try {
            account.withdraw(2000);
            assert false : "Une exception aurait dû être levée pour fonds insuffisants";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("Fonds insuffisants.");
        }
    }

    @Test
    void printStatement_shouldCallStatementPrinterWithTransactions() {
        account.deposit(1000);
        account.deposit(2000);
        account.withdraw(500);

        account.printStatement();

        // Vérifie que la méthode print de StatementPrinter a été appelée avec la liste des transactions
        verify(statementPrinter, times(1)).print(anyList());
    }
}