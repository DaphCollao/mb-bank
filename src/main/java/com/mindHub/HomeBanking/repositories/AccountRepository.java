package com.mindHub.HomeBanking.repositories;

import com.mindHub.HomeBanking.models.Account;
import com.mindHub.HomeBanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long>{
    Account findByNumber(String number);
    List<Account> findByClient(Client client);
}
