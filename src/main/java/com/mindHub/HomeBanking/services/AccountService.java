package com.mindHub.HomeBanking.services;

import com.mindHub.HomeBanking.dtos.AccountDTO;
import com.mindHub.HomeBanking.models.Account;
import com.mindHub.HomeBanking.models.Client;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface AccountService {
    List<AccountDTO> getAccountsDTO();
    AccountDTO getAccountDTO(Long id);
    List<Account> accountFindByClient(Client client);
    Account accountFindByNumber(String accountNumber);
    Account accountFindById(Long id);
    void saveAccount(Account account);

}
