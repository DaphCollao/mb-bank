package com.mindHub.HomeBanking;

import com.mindHub.HomeBanking.models.*;
import com.mindHub.HomeBanking.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@SpringBootTest
@AutoConfigureTestDatabase(replace = NONE)
public class RepositoriesTest {
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Test
    public void existLoans(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans,is(not(empty())));
    }
    @Test
    public void existPersonalLoan(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans, hasItem(hasProperty("name", is("personal"))));
    }

    @Test
    public void existUsers(){
        List<Client> clients = clientRepository.findAll();
        assertThat(clients,is(not(empty())));
    }
    @Test
    public void existClient(){
        List<Client> clients = clientRepository.findAll();
        assertThat(clients, hasItem(hasProperty("firstName", is("Melba"))));
    }

    @Test
    public void existAccounts(){
        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts,is(not(empty())));
    }
    @Test
    public void existAccountNumber(){
        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts, hasItem(hasProperty("number", is("VIN001"))));
    }

    @Test
    public void existCards(){
        List<Card> cards = cardRepository.findAll();
        assertThat(cards,is(not(empty())));
    }
    @Test
    public void existCardNumber(){
        List<Card> cards = cardRepository.findAll();
        assertThat(cards, hasItem(hasProperty("number", is("4545-1234-4567-4567"))));
    }

    @Test
    public void existTransaction(){
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions,is(not(empty())));
    }
    @Test
    public void existTransactionRef(){
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions, hasItem(hasProperty("referenceNum", is("asda63"))));
    }
}
