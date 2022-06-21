package com.mindHub.HomeBanking.repositories;

import com.mindHub.HomeBanking.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface LoanRepository extends JpaRepository<Loan, Long> {
    Loan findById(long id);
    Loan findByName(String loanName);
}
