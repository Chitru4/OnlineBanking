package com.example.onlinebanking.repository;

import com.example.onlinebanking.model.Account;
import com.example.onlinebanking.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountAndType(Account account, String type);
    List<Transaction> findByAccount(Account account);
    List<Transaction> findByAccountInOrderByTransactionId(List<Account> account);
    List<Transaction> findByTransferAccountIdIn(List<Long> transferAccountIds);
}