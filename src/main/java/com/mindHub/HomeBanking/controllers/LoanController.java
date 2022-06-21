package com.mindHub.HomeBanking.controllers;

import com.mindHub.HomeBanking.dtos.LoanApplicationDTO;
import com.mindHub.HomeBanking.dtos.LoanDTO;
import com.mindHub.HomeBanking.models.*;
import com.mindHub.HomeBanking.services.AccountService;
import com.mindHub.HomeBanking.services.ClientService;
import com.mindHub.HomeBanking.services.LoanService;
import com.mindHub.HomeBanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mindHub.HomeBanking.Utils.Utils.*;

@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    private LoanService loanService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ClientService clientService;

    @GetMapping("/loans")
    public List<LoanDTO> getLoans(){
        return loanService.getLoansDTO();
    }

    @GetMapping("/loans/{id}")
    public LoanDTO getLoan(@PathVariable Long id){
        return loanService.getLoanDTO(id);
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> applyLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication){

        // Local variable
        Loan loanInfo = loanService.loanFindById(loanApplicationDTO.getLoanId());
        Client currentClient = clientService.clientFindByEmail(authentication.getName());
        Account targetAccount = accountService.accountFindByNumber(loanApplicationDTO.getLoanAccountNumber());
        // Verifying missing data
        if (loanApplicationDTO.getAmountLoan() <= 0 || loanApplicationDTO.getLoanPayment() <= 0 || loanApplicationDTO.getLoanAccountNumber().isEmpty() || loanApplicationDTO.getLoanId() <= 0 || loanApplicationDTO.getLoanId() > 3 ){
            return new ResponseEntity<>("Missing data" , HttpStatus.FORBIDDEN);
        }

        // Verifying loan exists
        if (loanInfo == null){
            return new ResponseEntity<>("Loan doesn't exists" , HttpStatus.FORBIDDEN);
        }

        // Verifying Loan amount doesn't excess max Amount
        if (loanApplicationDTO.getAmountLoan() > loanInfo.getMaxAmount()){
            return new ResponseEntity<>("Loan amount exceeds max amount" , HttpStatus.FORBIDDEN);
        }

        // Verifying Payment is included in List Payment
        if (!loanInfo.getPayments().contains(loanApplicationDTO.getLoanPayment())){
            return new ResponseEntity<>("Loan payment not available" , HttpStatus.FORBIDDEN);
        }

        // Verifying Account exists
        if (targetAccount == null){
            return new ResponseEntity<>("Target Account doesn't exist" , HttpStatus.FORBIDDEN);
        }

        // Verifying Account belong to Current Client
        if (!currentClient.getAccounts().contains(targetAccount)){
            return new ResponseEntity<>("Account doesn't belong to client" , HttpStatus.FORBIDDEN);
        }

        // Max one loan of each
        if (currentClient.getLoans().contains(loanInfo) && loanInfo.getId() == 1){
            return new ResponseEntity<>("Already have mortgage loan" , HttpStatus.FORBIDDEN);
        } else if (currentClient.getLoans().contains(loanInfo) && loanInfo.getId() == 2) {
            return new ResponseEntity<>("Already have personal loan" , HttpStatus.FORBIDDEN);
        } else if (currentClient.getLoans().contains(loanInfo) && loanInfo.getId() == 3) {
            return new ResponseEntity<>("Already have auto loan" , HttpStatus.FORBIDDEN);
        };

        // Minimum amount loan
        if (loanApplicationDTO.getAmountLoan() < loanApplicationDTO.getAmountLoan()*(0.1)){
            return new ResponseEntity<>("Amount must be greater" , HttpStatus.FORBIDDEN);
        }
        // Loan Fee
        double amountLoanWithFee = 0;
        if (loanApplicationDTO.getLoanId() == 1){
            amountLoanWithFee = loanApplicationDTO.getAmountLoan()*loanInfo.getFee();
        } else if (loanApplicationDTO.getLoanId() == 2){
            amountLoanWithFee = loanApplicationDTO.getAmountLoan()*loanInfo.getFee();
        } else if (loanApplicationDTO.getLoanId() == 3){
            amountLoanWithFee = loanApplicationDTO.getAmountLoan()*loanInfo.getFee();
        }

        // Setting interest (debe ir en clientLoan con interes para que quede el registro de cuento se debe devolver)
        ClientLoan clientLoan = new ClientLoan(amountLoanWithFee, loanApplicationDTO.getLoanPayment(), currentClient, loanInfo);
        loanService.saveClientLoan(clientLoan);

        // Set balance in account
        double setBalance = targetAccount.getBalance() + loanApplicationDTO.getAmountLoan();

        // New Credit Transaction with loan
        String referenceNum;
        do {
            referenceNum = getRandomString(65, 90,4,new Random()).toUpperCase()+getRandomNumber(1000,9999);
        } while (transactionService.transactionFindByRef(referenceNum) != null);

        Transaction transaction = new Transaction(loanInfo.getName() + " loan approved",referenceNum, LocalDateTime.now(), loanApplicationDTO.getAmountLoan(), TransactionType.credit, targetAccount, setBalance);
        transactionService.saveTransaction(transaction);

        // Set new Balance amount to Account
        targetAccount.setBalance(setBalance);
        accountService.saveAccount(targetAccount);

        return new ResponseEntity<>("Loan created successfully", HttpStatus.CREATED);
    }

    @PostMapping("/createloans")
    public ResponseEntity<Object> create(
            @RequestParam String loanName,
            @RequestParam double loanMaxAmount,
            @RequestParam double loanFee,
            @RequestParam Set<Integer> loanPayments,
            Authentication authentication){

        Client adminCurrent = clientService.clientFindByEmail(authentication.getName());
        Loan loanInfo = loanService.loanFindByName(loanName);

        if (loanName.isEmpty() || loanMaxAmount <=0 || loanFee <= 0 || loanPayments.isEmpty()){
            return new ResponseEntity<>("You don't have the authority for that", HttpStatus.FORBIDDEN);
        }
        if (!adminCurrent.getEmail().contains("admin@mbb-admin.com")){
            return new ResponseEntity<>("You don't have the authority for that", HttpStatus.FORBIDDEN);
        }
        if (loanInfo != null){
            return new ResponseEntity<>("Loan already exist", HttpStatus.FORBIDDEN);
        }

        List<Integer> toList = loanPayments.stream().collect(Collectors.toList());

        Loan newLoan = new Loan(loanName, loanMaxAmount, loanFee, toList);
        loanService.saveLoan(newLoan);

        return new ResponseEntity<>("New loan created" , HttpStatus.CREATED);
    }
}
