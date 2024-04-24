package com.airtel.onlinebanking.repository;

import com.airtel.onlinebanking.model.Account;
import com.airtel.onlinebanking.model.User;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByUser(User user);
    List<Account> findByUserAndType(User user, String type);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Transactional
    Account findByAccountId(Long accountId);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Transactional
    @Query("SELECT a.balance from Account a where a.accountId=?1")
    Double getBalance(Long accountId);
    @Transactional
    @Modifying
    @Query("UPDATE Account a set a.balance=a.balance+?2 where a.accountId=?1")
    void setBalance(Long accountId, Double amount);
}
