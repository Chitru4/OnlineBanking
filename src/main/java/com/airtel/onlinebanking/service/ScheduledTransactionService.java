package com.airtel.onlinebanking.service;

import com.airtel.onlinebanking.model.Account;
import com.airtel.onlinebanking.model.ScheduledTransaction;
import com.airtel.onlinebanking.model.Transaction;
import com.airtel.onlinebanking.repository.AccountRepository;
import com.airtel.onlinebanking.repository.ScheduledTransactionRepository;
import com.airtel.onlinebanking.repository.TransactionRepository;
import com.airtel.onlinebanking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;

@Service
@Transactional
public class ScheduledTransactionService {
    private final ScheduledTransactionRepository scheduledTransactionRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionService transactionService;
    private final PriorityQueue<ScheduledTransaction> scheduledTransactions = new PriorityQueue<>(Comparator.comparing(ScheduledTransaction::getDueDateTime));
    @Autowired
    public ScheduledTransactionService(ScheduledTransactionRepository scheduledTransactionRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, TransactionService transactionService, UserRepository userRepository) {
        this.scheduledTransactionRepository = scheduledTransactionRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.transactionService = transactionService;
        this.userRepository = userRepository;
        this.scheduledTransactions.addAll(scheduledTransactionRepository.findAll());
    }

    /**
     * Create a new table for scheduled transactions
     * Every minute check if transaction is due and complete it
     * Set new date as current+increment
     * Set in memory priority queue to make it faster
     */
    @Scheduled(cron="0 */1 * * * *")
    public void doScheduledTransaction() {
        LocalDateTime currentTime = LocalDateTime.now();
        while (scheduledTransactions.peek() != null) {
            ScheduledTransaction scheduledTransaction = scheduledTransactions.poll();
            if (scheduledTransaction.getDueDateTime().isBefore(currentTime)) {
                Transaction transaction = new Transaction();
                transaction.setAccount(accountRepository.findByAccountId(scheduledTransaction.getFromAccountId()));
                transaction.setTransferAccountId(scheduledTransaction.getToAccountId());
                transaction.setAmount(scheduledTransaction.getAmount());
                transaction.setDescription("Auto-payed at:"+ LocalDateTime.now());
                if (transactionService.doTransaction("Admin", transaction, "-auto") != 1) {
                    continue;
                }
                if (scheduledTransaction.getCounter() == 1) {
                    scheduledTransactionRepository.delete(scheduledTransaction);
                    continue;
                }
                scheduledTransaction.setDueDateTime(scheduledTransaction.getDueDateTime().plusMinutes(scheduledTransaction.getMinuteIncrement()));
                scheduledTransaction.setCounter(scheduledTransaction.getCounter()-1);
                scheduledTransactions.add(scheduledTransaction);
                scheduledTransactionRepository.save(scheduledTransaction);
            }
            else {
                scheduledTransactions.add(scheduledTransaction);
                break;
            }
        }
    }

    public void saveScheduledTransaction(ScheduledTransaction scheduledTransaction) {
        scheduledTransaction.setDueDateTime(LocalDateTime.now());
        scheduledTransactions.add(scheduledTransaction);
        scheduledTransactionRepository.save(scheduledTransaction);
    }
    public List<ScheduledTransaction> getAllUserScheduledTransactions(String username) {
        List<Account> accounts = accountRepository.findByUser(userRepository.findByUsername(username));
        List<Long> accountIds = new ArrayList<>();
        for (Account account:accounts) {
            accountIds.add(account.getAccountId());
        }
        return scheduledTransactionRepository.findByFromAccountIdIn(accountIds);
    }
}
