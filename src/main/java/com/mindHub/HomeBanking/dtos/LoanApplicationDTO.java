package com.mindHub.HomeBanking.dtos;

import com.mindHub.HomeBanking.models.Account;
import java.util.List;
import com.mindHub.HomeBanking.models.Loan;

public class LoanApplicationDTO {
    private long loanId;
    private double amountLoan;
    private int loanPayment;
    private String loanAccountNumber;

    public LoanApplicationDTO(){};

    public LoanApplicationDTO(long loanId, double amountLoan, int loanPayment, String loanAccountNumber) {
        this.loanId = loanId;
        this.amountLoan = amountLoan;
        this.loanPayment = loanPayment;
        this.loanAccountNumber = loanAccountNumber;
    }

    public long getLoanId() {
        return loanId;
    }

    public double getAmountLoan() {
        return amountLoan;
    }
    public void setAmountLoan(double amountLoan) {
        this.amountLoan = amountLoan;
    }

    public int getLoanPayment() {
        return loanPayment;
    }
    public void setLoanPayment(int loanPayment) {
        this.loanPayment = loanPayment;
    }

    public String getLoanAccountNumber() {
        return loanAccountNumber;
    }
    public void setAccountNumber(String loanAccountNumber) {
        this.loanAccountNumber = loanAccountNumber;
    }
}
