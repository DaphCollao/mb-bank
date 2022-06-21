package com.mindHub.HomeBanking.controllers;

import com.mindHub.HomeBanking.dtos.TransactionDTO;
import com.mindHub.HomeBanking.models.Account;
import com.mindHub.HomeBanking.models.Client;
import com.mindHub.HomeBanking.models.Transaction;
import com.mindHub.HomeBanking.models.TransactionType;
import com.mindHub.HomeBanking.services.AccountService;
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

import static com.mindHub.HomeBanking.Utils.Utils.getRandomNumber;
import static com.mindHub.HomeBanking.Utils.Utils.getRandomString;


@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    public TransactionService transactionService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;

    @GetMapping("/transactions")
    public List<TransactionDTO> getTransactions(){
        return transactionService.getTransactionsDTO();
    }

    @GetMapping("/transaction/{id}")
    public TransactionDTO getTransaction(@PathVariable long id){
        return transactionService.getTransactionDTO(id);
    }

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> newtransaction (
            Authentication authentication,
            @RequestParam double amount,
            @RequestParam String description,
            @RequestParam String originNumber,
            @RequestParam String targetNumber){

        LocalDateTime dateTransaction = LocalDateTime.now();
        /* TransactionType transactionType =  ; */
        Account originAccount = accountService.accountFindByNumber(originNumber);
        Account targetAccount = accountService.accountFindByNumber(targetNumber);
        //
        if (amount <= 0){
            return new ResponseEntity<>("The amount must be greater than 0" , HttpStatus.FORBIDDEN);
        }

        if (description.isEmpty() || originNumber.isEmpty() || targetNumber.isEmpty()){
            return new ResponseEntity<>("Missing data" , HttpStatus.FORBIDDEN);
        }

        if(originAccount == null){
            return new ResponseEntity<>("Origin Account doesn't exist" , HttpStatus.FORBIDDEN);
        }
        if(!originAccount.isEnable()){
            return new ResponseEntity<>("Origin Account is disable" , HttpStatus.FORBIDDEN);
        }

        Client currentClient = clientService.clientFindByEmail(authentication.getName());
        if(!currentClient.getAccounts().contains(originAccount)){
            return new ResponseEntity<>("Origin Account doesn't belong to Client" , HttpStatus.FORBIDDEN);
        }

        if (originNumber.equals(targetNumber)){
            return new ResponseEntity<>("Same origin and destiny account" , HttpStatus.FORBIDDEN);
        }

        if(targetAccount == null){
            return new ResponseEntity<>("Target Account doesn't exist" , HttpStatus.FORBIDDEN);
        }

        if(originAccount.getBalance() < amount){
            return new ResponseEntity<>("Transaction amount exceeds account balance" , HttpStatus.FORBIDDEN);
        }

        // Current Balance after transfer
        double currentBalanceDebit = originAccount.getBalance() - amount;
        double currentBalanceCredit = targetAccount.getBalance() + amount;

        String referenceNum;
        do {
            referenceNum = getRandomString(65, 90,4,new Random()).toUpperCase()+getRandomNumber(1000,9999);
        } while (transactionService.transactionFindByRef(referenceNum) != null);

        Transaction transactionDebit = new Transaction(description + " - " + targetNumber, referenceNum ,dateTransaction, amount, TransactionType.debit, originAccount, currentBalanceDebit);
        transactionService.saveTransaction(transactionDebit);

        Transaction transactionCredit = new Transaction(description + " - " + originNumber, referenceNum ,dateTransaction, amount, TransactionType.credit, targetAccount, currentBalanceCredit);
        transactionService.saveTransaction(transactionCredit);

        originAccount.setBalance(currentBalanceDebit);
        accountService.saveAccount(originAccount);
        targetAccount.setBalance(currentBalanceCredit);
        accountService.saveAccount(targetAccount);

        return new ResponseEntity<>("Transaction successful", HttpStatus.CREATED);
    }
}
