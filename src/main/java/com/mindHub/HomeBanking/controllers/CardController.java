package com.mindHub.HomeBanking.controllers;

import com.mindHub.HomeBanking.dtos.CardDTO;
import com.mindHub.HomeBanking.dtos.CardPaymentDTO;
import com.mindHub.HomeBanking.models.*;
import com.mindHub.HomeBanking.services.AccountService;
import com.mindHub.HomeBanking.services.CardService;
import com.mindHub.HomeBanking.services.ClientService;
import com.mindHub.HomeBanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mindHub.HomeBanking.Utils.Utils.getRandomNumber;
import static com.mindHub.HomeBanking.Utils.Utils.getRandomString;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private CardService cardService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountService accountService;

    @GetMapping("/cards")
    public List<CardDTO> getCards(){
        return cardService.cardsDTO();
    }

    @GetMapping("/cards/{id}")
    public CardDTO getCard(@PathVariable Long id){
        return cardService.cardDTO(id);
    }

    @GetMapping("/clients/current/cards")
    public CardDTO getCardDTO(Long id){
        return cardService.cardDTO(id);
    }

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> creation(
            @RequestParam CardColor cardColor,
            @RequestParam CardType cardType,
            Authentication authentication ){

        Client currentClient = clientService.clientFindByEmail(authentication.getName());
        String cardHolder = currentClient.getFullName();
        LocalDateTime fromDate = LocalDateTime.now();
        LocalDateTime thruDate = fromDate.plusYears(5);
        String number;
        boolean statusTest = true;
        do {
        number = "4545" + "-" + getRandomNumber(1000,9999) + "-" + getRandomNumber(1000,9999) + "-" + getRandomNumber(1000,9999);
        } while (cardService.cardFindByNumber(number) != null);

        int cvv = getRandomNumber(100,999);

        // local Variables - set of cards
        Set<Card> debitCard = currentClient.getCards().stream().filter(card -> card.getCardType().equals(CardType.DEBIT) && card.isEnable()).collect(Collectors.toSet());

        Set<Card> debitGold = debitCard.stream().filter(color -> color.getCardColor() == (CardColor.GOLD) && color.isEnable()).collect(Collectors.toSet());
        Set<Card> debitSilver = debitCard.stream().filter(color -> color.getCardColor() == (CardColor.SILVER) && color.isEnable()).collect(Collectors.toSet());
        Set<Card> debitTitanium = debitCard.stream().filter(color -> color.getCardColor() == (CardColor.TITANIUM) && color.isEnable()).collect(Collectors.toSet());

        Set<Card> creditCard = currentClient.getCards().stream().filter(card -> card.getCardType()==(CardType.CREDIT) && card.isEnable()).collect(Collectors.toSet());

        Set<Card> creditGold = creditCard.stream().filter(color -> color.getCardColor()==(CardColor.GOLD) && color.isEnable()).collect(Collectors.toSet());
        Set<Card> creditSilver = creditCard.stream().filter(color -> color.getCardColor()==(CardColor.SILVER) && color.isEnable()).collect(Collectors.toSet());
        Set<Card> creditTitanium = creditCard.stream().filter(color -> color.getCardColor()==(CardColor.TITANIUM) && color.isEnable()).collect(Collectors.toSet());

        // limits to three per card type
        if (cardType == CardType.DEBIT){
            if (debitCard.size() >= 3) {
                return new ResponseEntity<>("Reached out the maximum number of debit cards", HttpStatus.FORBIDDEN);
            }
        } else if (cardType == CardType.CREDIT) {
            if (creditCard.size() >= 3) {
                return new ResponseEntity<>("Reached out the maximum number of credit cards", HttpStatus.FORBIDDEN);
            }
        }

        // Limits to one color per card type
        if (cardType == CardType.DEBIT && cardColor == CardColor.GOLD){
            if (debitGold.size()>=1){
                return new ResponseEntity<>("Already have a GOLD DEBIT card", HttpStatus.FORBIDDEN);
            }
        } else if (cardType == CardType.DEBIT && cardColor == CardColor.SILVER){
            if (debitSilver.size()>=1){
                return new ResponseEntity<>("Already have a SILVER DEBIT card", HttpStatus.FORBIDDEN);
            }
        } else if (cardType == CardType.DEBIT && cardColor == CardColor.TITANIUM){
            if (debitTitanium.size()>=1){
                return new ResponseEntity<>("Already have a TITANIUM DEBIT card", HttpStatus.FORBIDDEN);
            }
        }

        if (cardType == CardType.CREDIT && cardColor == CardColor.GOLD){
            if (creditGold.size()>=1){
                return new ResponseEntity<>("Already have a GOLD CREDIT card", HttpStatus.FORBIDDEN);
            }
        } else if (cardType == CardType.CREDIT && cardColor == CardColor.SILVER){
            if (creditSilver.size()>=1){
                return new ResponseEntity<>("Already have a SILVER CREDIT card", HttpStatus.FORBIDDEN);
            }
        } else if (cardType == CardType.CREDIT && cardColor == CardColor.TITANIUM){
            if (creditTitanium.size()>=1){
                return new ResponseEntity<>("Already have a TITANIUM CREDIT card", HttpStatus.FORBIDDEN);
            }
        }

        cardService.saveCard(new Card(currentClient, cardHolder, fromDate, thruDate, number, cvv, cardColor, cardType, true));

        return new ResponseEntity<>("New card created" , HttpStatus.CREATED);
    }


    @PatchMapping("/clients/current/cards")
    public ResponseEntity<Object> changeStatus(@RequestParam Long id, Authentication authentication){
        Client currentClient = clientService.clientFindByEmail(authentication.getName());
        Card card = cardService.cardFindById(id);

        if (!currentClient.getCards().contains(card)){
            return new ResponseEntity<>("Card doesn't belong to client", HttpStatus.FORBIDDEN);
        }
        if (cardService.cardFindById(id) == null){
            return new ResponseEntity<>("Card doesn't exist", HttpStatus.FORBIDDEN);
        }

        card.setEnable(false);
        cardService.saveCard(card);

        return new ResponseEntity<>("Card Status change to disable" , HttpStatus.ACCEPTED);
    }


    @Transactional
    @PostMapping("payment")
    public ResponseEntity<Object> cardPayment(@RequestBody CardPaymentDTO cardPaymentDTO){
        Card card = cardService.cardFindByNumber(cardPaymentDTO.getCardNumber());
        Client cardClient = card.getClient();
        Account clientAccount = cardClient.getAccounts().stream().findFirst().orElse(null);

        // Data entry not null
        if (cardPaymentDTO.getCardNumber().isEmpty() || cardPaymentDTO.getCardCvv() == 0 || cardPaymentDTO.getPaymentAmount() == 0 || cardPaymentDTO.getCardPaymentDescription().isEmpty()){
            return new ResponseEntity<>("Missing Data" , HttpStatus.FORBIDDEN);
        }
        // Correct Data Entry
        if (cardClient.getCards().contains(cardPaymentDTO.getCardNumber())){
            return new ResponseEntity<>("Card doesn't belong to client" , HttpStatus.FORBIDDEN);
        }
        if (card.getCvv() != cardPaymentDTO.getCardCvv()){
            return new ResponseEntity<>("Wrong security number" , HttpStatus.FORBIDDEN);
        }
        if (!cardClient.getFirstName().contains(cardPaymentDTO.getCardOwnerFirstName())){
            return new ResponseEntity<>("Wrong first name", HttpStatus.FORBIDDEN);
        }
        if (!cardClient.getLastName().contains(cardPaymentDTO.getCardOwnerLastName())){
            return new ResponseEntity<>("Wrong first name", HttpStatus.FORBIDDEN);
        }
        if (cardPaymentDTO.getPaymentAmount() >= clientAccount.getBalance()){
            return new ResponseEntity<>("Payment amount excess balance in account" , HttpStatus.FORBIDDEN);
        }

        // Card nor overdue
        LocalDateTime cardPaymentDate = LocalDateTime.now();
        if ( card.getThruDate().equals(cardPaymentDate) ){
            return new ResponseEntity<>("Card is expired" , HttpStatus.FORBIDDEN);
        }

        // Create transaction with payment information
        String referenceNum;
        do {
            referenceNum = getRandomString(65, 90,4,new Random()).toUpperCase()+getRandomNumber(1000,9999);
        } while (transactionService.transactionFindByRef(referenceNum) != null);

        double currentBalanceDebit = clientAccount.getBalance() - cardPaymentDTO.getPaymentAmount();

        Transaction transactionPayment = new Transaction(cardPaymentDTO.getCardPaymentDescription(), referenceNum,cardPaymentDate, cardPaymentDTO.getPaymentAmount(), TransactionType.debit, clientAccount, currentBalanceDebit);
        transactionService.saveTransaction(transactionPayment);

        // Save Account changes in balance
        clientAccount.setBalance(currentBalanceDebit);


        return new ResponseEntity<>("Payment successful" , HttpStatus.ACCEPTED);
    }

}
