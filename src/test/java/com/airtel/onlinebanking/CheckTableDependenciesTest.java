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
    private Transaction transaction1;
    private Transaction transaction2;
    private TestData testData;

    @BeforeEach
    public void setupUserData() {
        testData = new TestData();
        user = testData.getUser();
        userRepository.saveAndFlush(user);
        account = testData.getAccount1();
        account.setUser(user);
        accountRepository.saveAndFlush(account);
        transaction1 = testData.getTransaction1();
        transaction1.setAccount(account);
        transactionRepository.saveAndFlush(transaction1);
        transaction2 = testData.getTransaction2();
        transaction2.setAccount(account);
        transactionRepository.saveAndFlush(transaction2);
    }
    @Test
    void deleteUser_ShouldDeleteAccountsAndTransactions() {
        transactionRepository.delete(transaction1);
        transactionRepository.delete(transaction2);
        transactionRepository.flush();
        assertThat(transactionRepository.findByAccount(account)).isEmpty();
        accountRepository.delete(account);
        accountRepository.flush();
        assertThat(accountRepository.findByUserAndType(user, account.getType())).isEmpty();
        userRepository.delete(user);
        userRepository.flush();
        assertThat(userRepository.findByUsername(user.getUsername())).isNull();
    }
}
