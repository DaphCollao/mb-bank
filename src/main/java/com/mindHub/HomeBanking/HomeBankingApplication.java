package com.mindHub.HomeBanking;

import com.mindHub.HomeBanking.models.*;
import com.mindHub.HomeBanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class HomeBankingApplication {
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomeBankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
		return (args) -> {
			//Clients
			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("melba1"));
			clientRepository.save(client1);

			Client client2 = new Client("Juan", "Pérez", "perez@mindhub.com", passwordEncoder.encode("juan1"));
			clientRepository.save(client2);

			Client client3 = new Client("John", "Doe", "doe@mindhub.com", passwordEncoder.encode("john1"));
			clientRepository.save(client3);

			Client admin = new Client("admin", "admin", "admin@mbb-admin.com", passwordEncoder.encode("admin1"));
			clientRepository.save(admin);

			//Accounts (Accounts 1-2 Client 1 / Account 3 Client 2)
			LocalDateTime today = LocalDateTime.now();

			Account account1 = new Account("VIN001" , today , 5000.00, client1, AccountType.credit,true);
			accountRepository.save(account1);
			Account account2 = new Account("VIN002" , today.plusDays(1) , 7500.0, client1,AccountType.savings ,true);
			accountRepository.save(account2);

			Account account3 = new Account("VIN003" , today , 7500.0, client2, AccountType.credit, true);
			accountRepository.save(account3);

			Account account4 = new Account("VIN004" , today , 7500.0, client3, AccountType.credit,true);
			accountRepository.save(account4);

			//Transaction (Transactions 1-5 -> Accounts 1, Transactions 6-9 -> Account 2 Client 1 / Transaction3 -> Account 3 -> Client 2)
			Transaction transaction1 = new Transaction("groceries","asda54", today, 500.00, TransactionType.debit , account1,account1.getBalance()-500.00 );
			transactionRepository.save(transaction1);
			account1.setBalance(transaction1.getCurrentBalance());
			accountRepository.save(account1);

			Transaction transaction2 = new Transaction("payment","asda55", today, 700.00, TransactionType.credit , account1, account1.getBalance()+700.00);
			transactionRepository.save(transaction2);
			account1.setBalance(transaction2.getCurrentBalance());
			accountRepository.save(account1);

			Transaction transaction3 = new Transaction("health","asda56", today, 400.00, TransactionType.debit , account1, account1.getBalance()-400.00);
			transactionRepository.save(transaction3);
			account1.setBalance(transaction3.getCurrentBalance());
			accountRepository.save(account1);

			Transaction transaction4 = new Transaction("entertainment","asda57", today, 550.00, TransactionType.debit , account1, account1.getBalance()-550.00);
			transactionRepository.save(transaction4);
			account1.setBalance(transaction4.getCurrentBalance());
			accountRepository.save(account1);

			Transaction transaction5 = new Transaction("transfer","asda58", today, 450.00, TransactionType.credit , account1, account1.getBalance()+450.00);
			transactionRepository.save(transaction5);
			account1.setBalance(transaction5.getCurrentBalance());
			accountRepository.save(account1);

			Transaction transaction6 = new Transaction("netflix","asda59", today, 19.99, TransactionType.debit , account2, account2.getBalance()-19.99);
			transactionRepository.save(transaction6);
			account2.setBalance(transaction6.getCurrentBalance());
			Transaction transaction7 = new Transaction("clothes","asda60", today, 359.50, TransactionType.debit , account2, account2.getBalance()-359.50);
			transactionRepository.save(transaction7);
			account2.setBalance(transaction7.getCurrentBalance());
			Transaction transaction8 = new Transaction("groceries","asda61", today, 258.77, TransactionType.debit , account2, account2.getBalance()-258.77);
			transactionRepository.save(transaction8);
			account2.setBalance(transaction8.getCurrentBalance());
			Transaction transaction9 = new Transaction("payment","asda62", today, 584.58, TransactionType.credit , account2, account2.getBalance()+584.58);
			transactionRepository.save(transaction9);
			account2.setBalance(transaction9.getCurrentBalance());
			accountRepository.save(account2);

			Transaction transaction10 = new Transaction("payment","asda63", today, 584.58, TransactionType.credit , account3, account3.getBalance()+584.58);
			transactionRepository.save(transaction10);
			account3.setBalance(transaction10.getCurrentBalance());
			accountRepository.save(account3);

			//Types of Loans
			Loan mortgageLoan = new Loan("mortgage", 500000, 1.10, List.of(12,24,36,48,60));
			loanRepository.save(mortgageLoan);
			Loan personalLoan = new Loan("personal", 100000,1.20, List.of(6,12,24));
			loanRepository.save(personalLoan);
			Loan autoLoan = new Loan("auto", 300000,1.15, List.of(6,12,24,36));
			loanRepository.save(autoLoan);

			//Loan - clientLoan (clientLoan 1-2 -> client 1 / clientLoan 3-4 -> client 2)
			ClientLoan clientLoan1 = new ClientLoan(400000, 60, client1, mortgageLoan);
			clientLoanRepository.save(clientLoan1);
			ClientLoan clientLoan2 = new ClientLoan(50000, 12, client1, personalLoan);
			clientLoanRepository.save(clientLoan2);

			ClientLoan clientLoan3 = new ClientLoan(100000, 24, client2, personalLoan);
			clientLoanRepository.save(clientLoan3);
			ClientLoan clientLoan4 = new ClientLoan(20000, 36, client2, autoLoan);
			clientLoanRepository.save(clientLoan4);

			//Cards (card 1-2 -> client 1 / card 3 -> client 2)
			Card card1 = new Card(client1, client1.getFullName(), today, today.plusYears(5), "4545-1234-4567-4567", 159, CardColor.GOLD, CardType.DEBIT, true);
			cardRepository.save(card1);
			Card card2 = new Card(client1, client1.getFullName(),today, today.plusYears(5), "1231-4564-7897-7845", 951, CardColor.TITANIUM, CardType.CREDIT, true);
			cardRepository.save(card2);

			Card card3 = new Card(client2, client2.getFullName(),today.plusMonths(2), today.plusMonths(2).plusYears(5), "7897-4564-1232-1478", 753, CardColor.SILVER, CardType.DEBIT, true);
			cardRepository.save(card3);
		};
	}
}
