package com.mindHub.HomeBanking.repositories;

import com.mindHub.HomeBanking.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction findByReferenceNum(String referenceNum);
}
