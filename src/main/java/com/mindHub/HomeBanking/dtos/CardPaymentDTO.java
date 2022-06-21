package com.mindHub.HomeBanking.dtos;

public class CardPaymentDTO {
    private String cardNumber, cardPaymentDescription, cardOwnerFirstName, cardOwnerLastName;
    private int cardCvv;
    private double paymentAmount;

    public CardPaymentDTO(String cardNumber, String cardPaymentDescription, int cardCvv,String cardOwnerFirstName,String cardOwnerLastName, double paymentAmount) {
        this.cardNumber = cardNumber;
        this.cardPaymentDescription = cardPaymentDescription;
        this.cardCvv = cardCvv;
        this.cardOwnerFirstName = cardOwnerFirstName;
        this.cardOwnerLastName = cardOwnerLastName;
        this.paymentAmount = paymentAmount;

    }

    public String getCardNumber() {
        return cardNumber;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardPaymentDescription() {
        return cardPaymentDescription;
    }
    public void setCardPaymentDescription(String cardPaymentDescription) {
        this.cardPaymentDescription = cardPaymentDescription;
    }

    public int getCardCvv() {
        return cardCvv;
    }
    public void setCardCvv(int cardCvv) {
        this.cardCvv = cardCvv;
    }

    public String getCardOwnerFirstName() {
        return cardOwnerFirstName;
    }
    public void setCardOwnerName(String cardOwnerFirstName) {
        this.cardOwnerFirstName = cardOwnerFirstName;
    }

    public String getCardOwnerLastName() {
        return cardOwnerLastName;
    }
    public void setCardOwnerLastName(String cardOwnerLastName) {
        this.cardOwnerLastName = cardOwnerLastName;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }
    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
}
