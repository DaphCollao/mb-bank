package com.mindHub.HomeBanking.dtos;

import com.mindHub.HomeBanking.models.Card;
import com.mindHub.HomeBanking.models.CardColor;
import com.mindHub.HomeBanking.models.CardType;

import java.time.LocalDateTime;

public class CardDTO {
    private long id;
    private String cardHolder, number;
    private LocalDateTime fromDate, thruDate;
    private int cvv;
    private CardColor cardColor;
    private CardType cardType;
    private boolean enable;

    public CardDTO(){};
    public CardDTO(Card card) {
        this.id = card.getId();
        this.cardHolder = card.getCardHolder();
        this.fromDate = card.getFromDate();
        this.thruDate = card.getThruDate();
        this.number = card.getNumber();
        this.cvv = card.getCvv();
        this.cardColor = card.getCardColor();
        this.cardType = card.getCardType();
        this.enable = card.isEnable();
    }

    // Getters Class Card
    public long getId() {
        return id;
    }
    public String getNumber() {
        return number;
    }
    public String getCardHolder() {
        return cardHolder;
    }
    public LocalDateTime getFromDate() {
        return fromDate;
    }
    public LocalDateTime getThruDate() {
        return thruDate;
    }
    public int getCvv() {
        return cvv;
    }
    public CardColor getCardColor() {
        return cardColor;
    }
    public CardType getCardType() {
        return cardType;
    }
    public boolean isEnable() {
        return enable;
    }
}
