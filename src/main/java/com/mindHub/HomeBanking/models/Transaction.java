package com.mindHub.HomeBanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.crypto.spec.SecretKeySpec;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Random;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String description, referenceNum;
    private LocalDateTime date;
    private double amount, currentBalance;
    private TransactionType type;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    public Transaction(){};
    public Transaction(String description, String referenceNum,LocalDateTime date, double amount, TransactionType type, Account account, double currentBalance) {
        this.description = description;
        this.referenceNum = referenceNum;
        this.date = date;
        this.amount = amount;
        this.type = type;
        this.account = account;
        this.currentBalance = currentBalance;
    };

    //Getters And Setters
    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }
    public void setType(TransactionType type) {
        this.type = type;
    }

    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }
    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getReferenceNum() {
        return referenceNum;
    }

    public void setReferenceNum(String referenceNum) {
        this.referenceNum = referenceNum;
    }

}
