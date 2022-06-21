package com.mindHub.HomeBanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class ClientLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private double amountLoan;
    private int payment;

    //Relation ManyToOne ClientLoan - Client
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    //Relation ManyToOne ClientLoan - Loan
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loan_id")
    private Loan loan;

    //Constructors
    public ClientLoan(){};
    public ClientLoan(double amountLoan, int payment, Client client, Loan loan) {
        this.amountLoan = amountLoan;
        this.payment = payment;
        this.client = client;
        this.loan = loan;
    }

    //Getters & Setters
    public long getId() {
        return id;
    }

    public double getAmountLoan() {
        return amountLoan;
    }
    public void setAmountLoan(double amountLoan) {
        this.amountLoan = amountLoan;
    }

    public int getPayment() {
        return payment;
    }
    public void setPayment(int payment) {
        this.payment = payment;
    }

    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }

    public Loan getLoan() {
        return loan;
    }
    public void setLoan(Loan loan) {
        this.loan = loan;
    }
}
