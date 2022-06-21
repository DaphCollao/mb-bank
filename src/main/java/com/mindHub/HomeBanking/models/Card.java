package com.mindHub.HomeBanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String cardHolder;
    private LocalDateTime fromDate, thruDate;
    private String number;
    private int cvv;
    private CardColor cardColor;
    private CardType cardType;
    private boolean enable;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    //Constructors
    public Card(){};
    public Card(Client client, String cardHolder, LocalDateTime fromDate, LocalDateTime thruDate,
                String number, int cvv, CardColor cardColor, CardType cardType, boolean enable) {
        this.client = client;
        this.cardHolder = cardHolder;
        this.fromDate = fromDate;
        this.thruDate = thruDate;
        this.number = number;
        this.cvv = cvv;
        this.cardColor = cardColor;
        this.cardType = cardType;
        this.enable = enable;
    }

    //Getters & Setters Class Card
    public long getId() {
        return id;
    }

    public String getCardHolder() {
        return cardHolder;
    }
    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }
    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDateTime getThruDate() {
        return thruDate;
    }
    public void setThruDate(LocalDateTime thruDate) {
        this.thruDate = thruDate;
    }

    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    public int getCvv() {
        return cvv;
    }
    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public CardColor getCardColor() {
        return cardColor;
    }
    public void setCardColor(CardColor cardColor) {
        this.cardColor = cardColor;
    }

    public CardType getCardType() {
        return cardType;
    }
    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    // Prueba de Card Status
    public boolean isEnable() {
        return enable;
    }
    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    //Getter & Setter Relation ManyToOne card-client
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
}

