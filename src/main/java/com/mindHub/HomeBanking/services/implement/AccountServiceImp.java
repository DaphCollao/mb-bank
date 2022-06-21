package com.mindHub.HomeBanking.services.implement;

import com.mindHub.HomeBanking.dtos.AccountDTO;
import com.mindHub.HomeBanking.models.Account;
import com.mindHub.HomeBanking.models.Client;
import com.mindHub.HomeBanking.repositories.AccountRepository;
import com.mindHub.HomeBanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImp implements AccountService{
    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<AccountDTO> getAccountsDTO() {
        return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
    }

    @Override
    public AccountDTO getAccountDTO(Long id) {
        return accountRepository.findById(id).map(account -> new AccountDTO(account)).orElse(null);
    }

    @Override
    public List<Account> accountFindByClient(Client client) {
        return accountRepository.findByClient(client);
    }

    @Override
    public Account accountFindByNumber(String accountNumber) {
        return accountRepository.findByNumber(accountNumber);
    }

    @Override
    public Account accountFindById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }
}
