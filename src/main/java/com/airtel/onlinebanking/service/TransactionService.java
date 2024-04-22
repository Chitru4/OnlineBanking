package com.airtel.onlinebanking.service;

import com.airtel.onlinebanking.model.Account;
import com.airtel.onlinebanking.model.Transaction;
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
        Account creditAccount = accountRepository.findByAccountId(transaction.getTransferAccountId());
        Account debitAccount = transaction.getAccount();
        Transaction debitTransaction = new Transaction();
        Transaction creditTransaction = new Transaction();
        if (debitAccount == null || creditAccount == null || debitAccount.getAccountId().equals(creditAccount.getAccountId())) {
            return 0;
        }
//        if (pin != creditAccount.getPin()) {
//            return -2;
//        }
        if (transaction.getAmount()+getAmountSentToday(debitAccount)>dailyTransactionLimit) {
            return -3;
        }
        if (debitAccount.getBalance()<transaction.getAmount()) {
            return -1;
        }
        creditTransaction.setType("credit");
        creditTransaction.setAccount(creditAccount);
        creditTransaction.setTimeStamp(LocalDateTime.now());
        creditTransaction.setDescription(transaction.getDescription());
        creditTransaction.setAmount(transaction.getAmount());
        creditTransaction.setAccount(creditAccount);
        creditTransaction.setTransferAccountId(debitAccount.getAccountId());
        debitTransaction.setType("debit");
        debitTransaction.setAccount(debitAccount);
        debitTransaction.setTimeStamp(creditTransaction.getTimeStamp());
        debitTransaction.setDescription(transaction.getDescription());
        debitTransaction.setAmount(transaction.getAmount());
        debitTransaction.setAccount(debitAccount);
        debitTransaction.setTransferAccountId(creditAccount.getAccountId());

        creditAccount.setBalance(creditAccount.getBalance()+transaction.getAmount());
        debitAccount.setBalance(debitAccount.getBalance()-transaction.getAmount());

        transactionRepository.save(debitTransaction);
        transactionRepository.save(creditTransaction);
        return 1;
    }

    public int doFundTransaction(String username, Transaction transaction) {
        Account creditFundAccount = accountRepository.findByAccountId(transaction.getTransferAccountId());
        Account debitFundAccount = transaction.getAccount();
        Transaction debitFundTransaction = new Transaction();
        Transaction creditFundTransaction = new Transaction();
        System.out.println(creditFundAccount.getAccountId());
        System.out.println(debitFundAccount.getAccountId());
        if (debitFundAccount.getAccountId().equals(creditFundAccount.getAccountId())) {
            return 0;
        }
        if (debitFundAccount.getBalance()<transaction.getAmount()) {
            return -1;
        }
        creditFundTransaction.setType("credit-fund");
        creditFundTransaction.setAccount(creditFundAccount);
        creditFundTransaction.setTimeStamp(LocalDateTime.now());
        creditFundTransaction.setDescription(transaction.getDescription());
        creditFundTransaction.setAmount(transaction.getAmount());
        creditFundTransaction.setTransferAccountId(creditFundAccount.getAccountId());
        debitFundTransaction.setType("debit-fund");
        debitFundTransaction.setAccount(debitFundAccount);
        debitFundTransaction.setTimeStamp(creditFundTransaction.getTimeStamp());
        debitFundTransaction.setDescription(transaction.getDescription());
        debitFundTransaction.setAmount(transaction.getAmount());
        debitFundTransaction.setTransferAccountId(debitFundAccount.getAccountId());

        creditFundAccount.setBalance(creditFundAccount.getBalance()+transaction.getAmount());
        debitFundAccount.setBalance(debitFundAccount.getBalance()-transaction.getAmount());

        transactionRepository.save(debitFundTransaction);
        transactionRepository.save(creditFundTransaction);
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
        return transactionRepository.findByAccountIn(accountRepository.findByUser(userRepository.findByUsername(username)));
    }
}
