package com.mindHub.HomeBanking.repositories;

import com.mindHub.HomeBanking.models.Client;
import com.mindHub.HomeBanking.models.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByEmail(String email);
    Client findByToken(String token);
}

