package com.airtel.onlinebanking;

import com.airtel.onlinebanking.model.Account;
import com.airtel.onlinebanking.model.Transaction;
import com.airtel.onlinebanking.model.User;
import com.airtel.onlinebanking.repository.AccountRepository;
import com.airtel.onlinebanking.repository.TransactionRepository;
import com.airtel.onlinebanking.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CheckTableDependenciesTest {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    private User user;
    private Account account;
    private Transaction transaction;

    @BeforeEach
    public void setupUserData() {
        user = new User();
        user.setFirstName("dbc");
        user.setLastName("dde");
        user.setPanNumber("9241290724");
        user.setAddress("abcVille");
        user.setDob(LocalDate.parse("2002-09-22"));
        user.setEmail("bbc@abc.com");
        user.setMobile(997241894L);
        user.setUsername("train-test");
        user.setPassword("123");
        userRepository.saveAndFlush(user);
        account = new Account();
        account.setUser(user);
        account.setType("saving");
        account.setBalance(1000000D);
        account.setCreatedDate(LocalDateTime.now());
        accountRepository.saveAndFlush(account);
        transaction = new Transaction();
        transaction.setAccountType(account.getType());
        transaction.setAmount(10000D);
        transaction.setTimeStamp(LocalDateTime.now());
        transaction.setFromUsername(user.getUsername());
        transaction.setToUsername(user.getUsername());
        transaction.setAccount(account);
        transactionRepository.saveAndFlush(transaction);
    }
    @Test
    void deleteUser_ShouldDeleteAccountsAndTransactions() {
        userRepository.delete(user);
        userRepository.flush();
        assertThat(userRepository.findByUsername(user.getUsername())).isNotNull();
        accountRepository.delete(account);
        accountRepository.flush();
        assertThat(accountRepository.findByUserAndType(user, account.getType())).isNotNull();
        transactionRepository.delete(transaction);
        transactionRepository.flush();
        assertThat(transactionRepository.findByFromUsername(user.getUsername())).isEmpty();
        accountRepository.delete(account);
        accountRepository.flush();
        assertThat(accountRepository.findByUserAndType(user, account.getType())).isNull();
        userRepository.delete(user);
        userRepository.flush();
        assertThat(userRepository.findByUsername(user.getUsername())).isNull();
    }
}
