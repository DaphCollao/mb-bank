package com.mindHub.HomeBanking.services.implement;

import com.mindHub.HomeBanking.dtos.LoanDTO;
import com.mindHub.HomeBanking.models.ClientLoan;
import com.mindHub.HomeBanking.models.Loan;
import com.mindHub.HomeBanking.repositories.ClientLoanRepository;
import com.mindHub.HomeBanking.repositories.LoanRepository;
import com.mindHub.HomeBanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanServiceImp implements LoanService {
    @Autowired
    LoanRepository loanRepository;

    @Override
    public List<LoanDTO> getLoansDTO() {
        return loanRepository.findAll().stream().map(loan -> new LoanDTO(loan)).collect(Collectors.toList());
    }

    @Override
    public LoanDTO getLoanDTO(Long id) {
        return loanRepository.findById(id).map(loan -> new LoanDTO(loan)).orElse(null);
    }

    @Override
    public Loan loanFindById(Long id) {
        return loanRepository.findById(id).orElse(null);
    }

    @Override
    public Loan loanFindByName(String loanName) {
        return loanRepository.findByName(loanName);
    }

    @Autowired
    ClientLoanRepository clientLoanRepository;
    @Override
    public void saveClientLoan(ClientLoan clientLoan) {
        clientLoanRepository.save(clientLoan);
    }

    @Override
    public void saveLoan(Loan loan) {
        loanRepository.save(loan);
    }
}
