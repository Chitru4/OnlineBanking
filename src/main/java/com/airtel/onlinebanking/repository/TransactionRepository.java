package com.airtel.onlinebanking.repository;

import com.airtel.onlinebanking.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByFromUsername(String username);
    List<Transaction> findByToUsername(String username);
}
