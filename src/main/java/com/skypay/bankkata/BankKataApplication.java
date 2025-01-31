package com.skypay.bankkata;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankKataApplication {

	public static void main(String[] args) {
		Account account = new Account();

		account.deposit(1000);
		account.deposit(2000);
		account.withdraw(500);

		account.printStatement();
	}
}