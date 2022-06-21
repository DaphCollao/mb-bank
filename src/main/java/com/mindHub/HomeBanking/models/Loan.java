package com.mindHub.HomeBanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String name;
    private double maxAmount, fee;

    //relation OneToMany loan-payments w/ @ElementCollection
    @ElementCollection
    @Column(name = "payment")
    private List<Integer> payments = new ArrayList<>();

    //Relation OneToMany loan-ClientLoan
    @OneToMany(mappedBy = "loan", fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();

    //Constructors
    public Loan(){};
    public Loan(String name, double maxAmount, double fee, List<Integer> payments) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.fee = fee;
        this.payments = payments;
    }

    //Getter & Setters Class
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getMaxAmount() {
        return maxAmount;
    }
    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public double getFee() {
        return fee;
    }
    public void setFee(double fee) {
        this.fee = fee;
    }

    public List<Integer> getPayments() {
        return payments;
    }
    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }

    //Getter relation OneToMany
    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }
    public void addClientLoans(ClientLoan clientLoan) {
        clientLoan.setLoan(this);
        clientLoans.add(clientLoan);
    }
    @JsonIgnore

    public List<Client> getClients(){
        return clientLoans.stream().map(client -> client.getClient()).collect(Collectors.toList());
    }
}
