package com.mindHub.HomeBanking.dtos;

import com.mindHub.HomeBanking.models.Account;
import com.mindHub.HomeBanking.models.AccountType;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {
    private long id;
    private String number;
    private LocalDateTime creationDate;
    private double balance;
    private Set<TransactionDTO> transactions = new HashSet<>();
    private AccountType accountType;
    private boolean enable;
    //Constructors
    public AccountDTO(){}
    public AccountDTO(Account account){
        this.id = account.getId();
        this.number = account.getNumber();;
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.transactions = account.getTransactions().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toSet());
        this.accountType = account.getAccountType();
        this.enable = account.isEnable();
    }

    //Getters & Setters AccountDTO
    public long getId() {
        return id;
    }
    public String getNumber() {
        return number;
    }
    public LocalDateTime getCreationDate() {
        return creationDate;
    }
    public double getBalance() {
        return balance;
    }
    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }
    public AccountType getAccountType() {
        return accountType;
    }
    public boolean isEnable() {
        return enable;
    }
}
