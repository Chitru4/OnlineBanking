package com.airtel.onlinebanking.repository;

import com.airtel.onlinebanking.model.Account;
import com.airtel.onlinebanking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUserAndType(User user,String type);
    List<Account> findByUser(User user);
}
