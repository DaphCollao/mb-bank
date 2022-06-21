package com.mindHub.HomeBanking.services;

import com.mindHub.HomeBanking.dtos.LoanDTO;
import com.mindHub.HomeBanking.models.ClientLoan;
import com.mindHub.HomeBanking.models.Loan;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface LoanService {
    //Loan
    List<LoanDTO> getLoansDTO();
    LoanDTO getLoanDTO(@PathVariable Long id);
    Loan loanFindById(Long id);
    Loan loanFindByName(String loanName);
    //ClientLoan
    void saveClientLoan(ClientLoan clientLoan);
    void saveLoan(Loan loan);
}
