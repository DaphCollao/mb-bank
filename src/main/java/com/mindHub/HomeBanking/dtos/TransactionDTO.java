package com.mindHub.HomeBanking.dtos;

import com.mindHub.HomeBanking.models.Transaction;
import com.mindHub.HomeBanking.models.TransactionType;

import java.time.LocalDateTime;

public class TransactionDTO {
    private long id;
    private String description, referenceNum;
    private LocalDateTime date;
    private double amount, currentBalance;
    private TransactionType type;

    //Constructors
    public TransactionDTO(){};
    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.description = transaction.getDescription();
        this.referenceNum = transaction.getReferenceNum();
        this.date = transaction.getDate();
        this.amount = transaction.getAmount();
        this.type = transaction.getType();
        this.currentBalance = transaction.getCurrentBalance();
    }

    //Getter & Setter TransactionDTO

    public long getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }
    public String getReferenceNum() {
        return referenceNum;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public double getAmount() {
        return amount;
    }
    public TransactionType getType() {
        return type;
    }
    public double getCurrentBalance() {
        return  currentBalance;
    }
}
