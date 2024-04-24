package com.airtel.onlinebanking.repository;

import com.airtel.onlinebanking.model.Account;
import com.airtel.onlinebanking.model.ScheduledTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduledTransactionRepository extends JpaRepository<ScheduledTransaction, Long> {
    List<ScheduledTransaction> findByFromAccountIdIn(List<Long> accountIds);
}
