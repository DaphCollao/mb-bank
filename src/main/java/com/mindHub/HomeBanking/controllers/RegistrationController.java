package com.mindHub.HomeBanking.controllers;

import com.mindHub.HomeBanking.models.Client;
import com.mindHub.HomeBanking.services.ClientService;
import com.mindHub.HomeBanking.services.implement.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

import static com.mindHub.HomeBanking.Utils.Utils.deleteToken;


@RestController
@RequestMapping("/api")
public class RegistrationController {
    @Autowired
    UserServiceImp userServiceImp;
    @Autowired
    ClientService clientService;

    @GetMapping("/registrationConfirm/{token}")
    public ResponseEntity<Object> confirmRegistration(HttpServletRequest request, @PathVariable String token) {
        Client client = userServiceImp.getClient(token);
        String tokenToDelete = client.getToken();

        if (tokenToDelete == null || client.isEnabled()) {
            return new ResponseEntity<>("Invalid token" , HttpStatus.FORBIDDEN);
        }

        client.setEnabled(true);
        clientService.saveClient(client);
        deleteToken(tokenToDelete);

        return new ResponseEntity<>("You're validated" , HttpStatus.ACCEPTED);
    }
}
