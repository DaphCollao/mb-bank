package com.mindHub.HomeBanking.controllers;

import com.mindHub.HomeBanking.dtos.ClientDTO;
import com.mindHub.HomeBanking.models.Account;
import com.mindHub.HomeBanking.models.AccountType;
import com.mindHub.HomeBanking.models.Client;
import com.mindHub.HomeBanking.services.AccountService;
import com.mindHub.HomeBanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.mindHub.HomeBanking.Utils.Utils.getRandomNumber;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/clients")
    public List<ClientDTO> getClients() {
        return clientService.getClientsDTO();
    }

    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable long id) {
        return clientService.getClientDTO(id);
    }

    @GetMapping("/clients/current")
    public ClientDTO  getCurrentClient(Authentication authentication) {
        return new ClientDTO(clientService.getCurrentClient(authentication));
    }

    @GetMapping("/authenticated")
    public ResponseEntity<Object> isAuthenticated(Authentication authentication){
        if (authentication != null){
            return new ResponseEntity<>("Authenticated", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Not authenticated", HttpStatus.ACCEPTED);
    }

    @PostMapping("/clients")
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (clientService.clientFindByEmail(email) != null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }

        Client newClient = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientService.saveClient(newClient);

        /* ===== Generate new Account when Register a New Client ===== */
        // Generate random number
        String number = "VIN" + getRandomNumber(9999999, 100000000);
        // Creation Date of New Account
        LocalDateTime creationDate = LocalDateTime.now();
        // Initial Balance
        double balance = 0;
        boolean enable = true;
        Account account = new Account(number, creationDate, balance, newClient, AccountType.credit , enable);
        accountService.saveAccount(account);

        return new ResponseEntity<>("New registration successful", HttpStatus.CREATED);
    }
}
