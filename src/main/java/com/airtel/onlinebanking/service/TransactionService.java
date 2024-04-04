package com.airtel.onlinebanking.service;

import com.airtel.onlinebanking.model.Account;
import com.airtel.onlinebanking.model.Transaction;
import com.airtel.onlinebanking.model.User;
import com.airtel.onlinebanking.repository.AccountRepository;
import com.airtel.onlinebanking.repository.TransactionRepository;
import com.airtel.onlinebanking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    @Autowired
    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    public int doTransaction(String username, Transaction transaction) {
        User user = userRepository.findByUsername(username);
        Account accountFrom = accountRepository.findByUserAndType(user,transaction.getAccountType());
        transaction.setFromUsername(user.getUsername());
        transaction.setTimeStamp(LocalDateTime.now());
        if (accountFrom == null) {
            return 0;
        }
        Account accountTo = accountRepository.findByUserAndType(userRepository.findByUsername(transaction.getToUsername()), "saving");
        if (accountTo == null) {
            return -2;
        }
        Long currentBalance = accountFrom.getBalance();
        Long transactionAmount = transaction.getAmount();
        if (currentBalance>=transactionAmount) {
            accountFrom.setBalance(currentBalance-transactionAmount);
            accountTo.setBalance(accountTo.getBalance()+transactionAmount);
        }
        else {
            return -1;
        }
        transactionRepository.save(transaction);
        return 1;
    }
}
