package com.mindHub.HomeBanking.controllers;

import com.mindHub.HomeBanking.dtos.AccountDTO;
import com.mindHub.HomeBanking.models.Account;
import com.mindHub.HomeBanking.models.AccountType;
import com.mindHub.HomeBanking.models.Client;
import com.mindHub.HomeBanking.services.AccountService;
import com.mindHub.HomeBanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.Repository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.mindHub.HomeBanking.Utils.Utils.getRandomNumber;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        return accountService.getAccountsDTO();
    }

    @GetMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable long id){
        return accountService.getAccountDTO(id);
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> create(@RequestParam AccountType accountType, Authentication authentication){
        // Client
        Client currentClient = clientService.getCurrentClient(authentication);
        List<Account> accounts = accountService.accountFindByClient(currentClient);
        List<Account> accoutsFiltered = accounts.stream().filter(account -> account.isEnable()).collect(Collectors.toList());
        // Generate random number
        String number;
        do {
            number = "VIN" + getRandomNumber(0, 99999999);
        } while (accountService.accountFindByNumber(number) != null);
        // Date of creation of Account
        LocalDateTime creationDate = LocalDateTime.now();
        // Initial Balance
        double balance = 0;
        boolean enable = true;

        if (accountType == null){
            return new ResponseEntity<>("Missing Data", HttpStatus.FORBIDDEN);
        }

        if (accoutsFiltered.size() >= 3) {
            return new ResponseEntity<>("You reached out the maximum number of accounts", HttpStatus.FORBIDDEN);
        }


        Account newAccount = new Account(number, creationDate, balance, currentClient, accountType ,enable);
        accountService.saveAccount(newAccount);

        return new ResponseEntity<>("New Account successfully created " , HttpStatus.CREATED);
    }

    @PatchMapping("/clients/current/accounts")
    public ResponseEntity<Object> changeAccountStatus(@RequestParam Long id, Authentication authentication){
        Client currentClient = clientService.clientFindByEmail(authentication.getName());
        Account accountDisable = accountService.accountFindById(id);

        if (!currentClient.getAccounts().contains(accountDisable)){
            return new ResponseEntity<>("Account doesn't belong to client", HttpStatus.FORBIDDEN);
        }
        if (!accountDisable.isEnable()){
            return new ResponseEntity<>("Account already disable", HttpStatus.FORBIDDEN);
        }

        accountDisable.setEnable(false);
        accountService.saveAccount(accountDisable);

        return  new ResponseEntity<>("Account successfully disable", HttpStatus.ACCEPTED);
    }
}
