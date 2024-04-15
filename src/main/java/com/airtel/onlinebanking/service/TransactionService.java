package com.airtel.onlinebanking.service;

import com.airtel.onlinebanking.model.Account;
import com.airtel.onlinebanking.model.Transaction;
import com.airtel.onlinebanking.model.User;
import com.airtel.onlinebanking.repository.AccountRepository;
import com.airtel.onlinebanking.repository.TransactionRepository;
import com.airtel.onlinebanking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final static Double dailyTransactionLimit = 1_000_000D;
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
        if (getAmountSentToday(user)+transaction.getAmount()>dailyTransactionLimit) {
            return -3;
        }
        Account accountTo = accountRepository.findByUserAndType(userRepository.findByUsername(transaction.getToUsername()), "saving");
        if (accountTo == null) {
            return -2;
        }
        Double currentBalance = accountFrom.getBalance();
        Double transactionAmount = transaction.getAmount();
        if (currentBalance>=transactionAmount) {
            accountFrom.setBalance(currentBalance-transactionAmount);
            accountTo.setBalance(accountTo.getBalance()+transactionAmount);
        }
        else {
            return -1;
        }
        transaction.setAccount(accountFrom);
        transactionRepository.save(transaction);
        return 1;
    }

    public Double getAmountSentToday(User user) {
        Account account = accountRepository.findByUserAndType(user, "saving");
        List<Transaction> transactions = account.getTransactions();
        Double totalAmountSpendToday = 0D;
        if (transactions != null) {
            for (Transaction transaction : transactions) {
                if (transaction.getTimeStamp().isAfter(LocalDate.now().atTime(LocalTime.MIN)) && transaction.getTimeStamp().isBefore(LocalDate.now().atTime(LocalTime.MAX))) {
                    totalAmountSpendToday += transaction.getAmount();
                }
            }
        }
        return totalAmountSpendToday;
    }

    public List<Transaction> getSentTransactions(String username) {
        return transactionRepository.findByFromUsername(username);
    }
    public List<Transaction> getReceivedTransactions(String username) {
        return transactionRepository.findByToUsername(username);
    }
}
