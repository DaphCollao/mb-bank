package com.mindHub.HomeBanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String firstName, lastName, email, password;
    @OneToMany(mappedBy = "client" , fetch = FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();

    @OneToMany(mappedBy = "client" , fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();

    // Verification with Email, property start false
    @Column(name = "enabled")
    private boolean enabled;
    private String token;

    //Constructors
    public Client(){};
    public Client(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    // Getter & Setter Verification
    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    //Getter & Setter Class Client
    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    //Getters & Setters OneToMany
    public Set<Account> getAccounts() {
        return accounts;
    }
    public void addAccount(Account account){
        account.setClient(this);
        accounts.add(account);
    }

    public Set<Card> getCards(){
        return cards;
    }
    public void addCards(Card card){
        card.setClient(this);
        cards.add(card);
    }
    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }
    public void addClientLoans(ClientLoan clientLoan) {
        clientLoan.setClient(this);
        clientLoans.add(clientLoan);
    }

    @JsonIgnore
    public List<Loan> getLoans(){
        return clientLoans.stream().map(client -> client.getLoan()).collect(Collectors.toList());
    }

    //FullName for CardHolder
    public String getFullName(){
        return firstName + " " + lastName;
    }
}
