package com.example.onlinebanking.service;

import com.example.onlinebanking.model.Account;
import com.example.onlinebanking.model.Transaction;
import com.example.onlinebanking.repository.AccountRepository;
import com.example.onlinebanking.repository.TransactionRepository;
import com.example.onlinebanking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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
    public int doTransaction(String username, Transaction transaction, String type) {
        Account creditAccount = accountRepository.findByAccountId(transaction.getTransferAccountId());
        Account debitAccount = transaction.getAccount();
        Transaction creditTransaction = new Transaction();
        Transaction debitTransaction = new Transaction();
        if (debitAccount == null || creditAccount == null || debitAccount.getAccountId().equals(creditAccount.getAccountId())) {
            return 0;
        }
        else if(type.isEmpty() && accountRepository.findByUser(userRepository.findByUsername(username)).contains(creditAccount)) {
            return -2;
        }
        else if (accountRepository.getBalance(debitAccount.getAccountId())<transaction.getAmount()) {
            return -1;
        }
        else if (transaction.getAmount()+getAmountSentToday(debitAccount)>dailyTransactionLimit) {
            return -3;
        }

        transaction.setType(type);
        transaction.setTimeStamp(LocalDateTime.now());

        accountRepository.setBalance(creditAccount.getAccountId(), transaction.getAmount());
        accountRepository.setBalance(debitAccount.getAccountId(), -transaction.getAmount());

        transactionRepository.save(transaction);
        return 1;
    }
    public Double getAmountSentToday(Account account) {
        List<Transaction> transactions = transactionRepository.findByAccountAndType(account, account.getType());
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

    public List<Transaction> getTransactions(String username) {
        return transactionRepository.findByAccountInOrderByTransactionId(accountRepository.findByUser(userRepository.findByUsername(username)));
    }
    public List<Transaction> getTransactionsByTransferAccountId(String username) {
        List<Account> userAccounts = accountRepository.findByUser(userRepository.findByUsername(username));
        List<Long> transferAccountIds = new ArrayList<>();
        for (Account account : userAccounts) {
            transferAccountIds.add(account.getAccountId());
        }
        return transactionRepository.findByTransferAccountIdIn(transferAccountIds);
    }
}
