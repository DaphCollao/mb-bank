package com.mindHub.HomeBanking.services.implement;

import com.mindHub.HomeBanking.dtos.TransactionDTO;
import com.mindHub.HomeBanking.models.Transaction;
import com.mindHub.HomeBanking.repositories.TransactionRepository;
import com.mindHub.HomeBanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImp implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Override
    public List<TransactionDTO> getTransactionsDTO() {
        return transactionRepository.findAll().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toList());
    }

    @Override
    public TransactionDTO getTransactionDTO(long id) {
        return transactionRepository.findById(id).map(transaction -> new TransactionDTO(transaction)).orElse(null);
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public Transaction transactionFindByRef(String referenceNum) {
        return transactionRepository.findByReferenceNum(referenceNum);
    }
}
