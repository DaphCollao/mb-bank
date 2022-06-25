package com.mindHub.HomeBanking.services;

import com.mindHub.HomeBanking.models.Client;
import com.mindHub.HomeBanking.models.VerificationToken;

public interface UserService {
    Client getClient(String token);
    VerificationToken getVerificationToken(Client client);




}
