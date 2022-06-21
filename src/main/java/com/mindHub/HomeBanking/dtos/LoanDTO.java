package com.mindHub.HomeBanking.dtos;

import com.mindHub.HomeBanking.models.Loan;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class LoanDTO{
    private long id;
    private String name;
    private double maxAmount;
    private List<Integer> payments = new ArrayList<>();
    private double fee;
    private Set<ClientLoanDTO> clientLoans = new HashSet<>();

    public LoanDTO(){};
    public LoanDTO(Loan loan) {
        this.id = loan.getId();
        this.name = loan.getName();
        this.maxAmount = loan.getMaxAmount();
        this.fee = loan.getFee();
        this.payments = loan.getPayments();
        this.clientLoans = loan.getClientLoans().stream().map(clientLoan -> new ClientLoanDTO(clientLoan)).collect(Collectors.toSet());
    }

    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getMaxAmount() {
        return maxAmount;
    }
    public double getFee() {
        return fee;
    }
    public List<Integer> getPayments() {
        return payments;
    }
    public Set<ClientLoanDTO> getClientLoans() {
        return clientLoans;
    }
}
