package com.mindHub.HomeBanking.repositories;

import com.mindHub.HomeBanking.models.Client;
import com.mindHub.HomeBanking.models.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
    VerificationToken findByClient(Client client);
}
