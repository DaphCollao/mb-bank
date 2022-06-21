package com.mindHub.HomeBanking.dtos;

import com.mindHub.HomeBanking.models.ClientLoan;


public class ClientLoanDTO {
    private long id, loanId;
    private String nameLoan;
    private double amountLoan;
    private int payment;

    public ClientLoanDTO(){};
    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.loanId = clientLoan.getLoan().getId();
        this.nameLoan = clientLoan.getLoan().getName();
        this.amountLoan = clientLoan.getAmountLoan();
        this.payment = clientLoan.getPayment();
    }

    //Getters & Setters
    public long getId() {
        return id;
    }
    public double getAmountLoan() {
        return amountLoan;
    }
    public int getPayment() {
        return payment;
    }
    public long getLoanId() {
        return loanId;
    }
    public String getName() {
        return nameLoan;
    }
}
