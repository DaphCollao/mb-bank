package com.mindHub.HomeBanking.services.implement;

import com.mindHub.HomeBanking.models.Client;
import com.mindHub.HomeBanking.models.VerificationToken;
import com.mindHub.HomeBanking.repositories.ClientRepository;
import com.mindHub.HomeBanking.repositories.VerificationTokenRepository;
import com.mindHub.HomeBanking.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private ClientRepository clientRepository;


    @Override
    public Client getClient(String  token) {
        return clientRepository.findByToken(token);
    }

    @Override
    public VerificationToken getVerificationToken(Client client) {
        return verificationTokenRepository.findByClient(client);
    }


}
