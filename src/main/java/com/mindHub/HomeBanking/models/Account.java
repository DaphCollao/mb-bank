package com.mindHub.HomeBanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String number;
    private LocalDateTime creationDate;
    private double balance;
    private AccountType accountType;
    private boolean enable;

    //ManyToOne Account-Client
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private  Client client;

    //OneToMany Account-Transaction relationship
    @OneToMany(mappedBy = "account" , fetch = FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();

    public Account (){};
    public Account(String number, LocalDateTime creationDate, double balance, Client client, AccountType accountType, boolean enable) {
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
        this.client = client;
        this.accountType = accountType;
        this.enable = enable;
    }

    //Getter & Setter class Account
    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public AccountType getAccountType() {
        return accountType;
    }
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public boolean isEnable() {
        return enable;
    }
    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    //Get de ManyToOne Client-Account
    public Client getClient(){
        return client;
    }
    public void setClient(Client client){
        this.client = client;
    }

    //Set de OneToMany Account-Transaction
    public Set<Transaction> getTransactions() {
        return transactions;
    }
    public void addTransaction(Transaction transaction){
        transaction.setAccount(this);
        transactions.add(transaction);
    }
}
