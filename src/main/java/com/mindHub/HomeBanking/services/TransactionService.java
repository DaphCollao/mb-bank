package com.mindHub.HomeBanking.services;

import com.mindHub.HomeBanking.dtos.TransactionDTO;
import com.mindHub.HomeBanking.models.Transaction;

import java.util.List;

public interface TransactionService {
    List<TransactionDTO> getTransactionsDTO();
    TransactionDTO getTransactionDTO(long id);
    void saveTransaction(Transaction transaction);
    Transaction transactionFindByRef(String referenceNum);
}
