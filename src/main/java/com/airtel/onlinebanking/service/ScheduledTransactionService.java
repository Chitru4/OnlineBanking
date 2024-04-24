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
import java.util.List;
import java.util.ArrayList;

@Service
@Transactional
public class ScheduledTransactionService {
    private final ScheduledTransactionRepository scheduledTransactionRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionService transactionService;
    @Autowired
    public ScheduledTransactionService(ScheduledTransactionRepository scheduledTransactionRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, TransactionService transactionService, UserRepository userRepository) {
        this.scheduledTransactionRepository = scheduledTransactionRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.transactionService = transactionService;
        this.userRepository = userRepository;
    }

    /**
     * Create a new table for scheduled transactions
     * Every minute check if transaction is due and complete it
     * Set new date as current+increment
     */
    @Scheduled(cron="0 */1 * * * *", zone = "GMT+5:30")
    public void doScheduledTransaction() {
        List<ScheduledTransaction> scheduledTransactionList = scheduledTransactionRepository.findAll();
        LocalDateTime currentTime = LocalDateTime.now();
        for (ScheduledTransaction scheduledTransaction:scheduledTransactionList) {
            if (scheduledTransaction.getDueDateTime().isBefore(currentTime)) {
                Transaction transaction = new Transaction();
                transaction.setAccount(accountRepository.findByAccountId(scheduledTransaction.getFromAccountId()));
                transaction.setTransferAccountId(scheduledTransaction.getToAccountId());
                transaction.setAmount(scheduledTransaction.getAmount());
                transaction.setDescription("Auto-payed at:"+ LocalDateTime.now());
                transactionService.doTransaction("Admin", transaction, "-auto");
                if (scheduledTransaction.getCounter() == 1) {
                    scheduledTransactionRepository.delete(scheduledTransaction);
                    continue;
                }
                scheduledTransaction.setDueDateTime(scheduledTransaction.getDueDateTime().plusMinutes(scheduledTransaction.getMinuteIncrement()));
                scheduledTransaction.setCounter(scheduledTransaction.getCounter()-1);
            }
        }
    }

    public void saveScheduledTransaction(ScheduledTransaction scheduledTransaction) {
        scheduledTransaction.setDueDateTime(LocalDateTime.now());
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
