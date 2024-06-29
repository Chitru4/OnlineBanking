package com.example.onlinebanking;

import com.example.onlinebanking.model.Account;
import com.example.onlinebanking.model.Transaction;
import com.example.onlinebanking.model.User;
import com.example.onlinebanking.repository.AccountRepository;
import com.example.onlinebanking.repository.TransactionRepository;
import com.example.onlinebanking.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
        Assertions.assertThat(transactionRepository.findByAccount(account)).isEmpty();
        accountRepository.delete(account);
        accountRepository.flush();
        Assertions.assertThat(accountRepository.findByUserAndType(user, account.getType())).isEmpty();
        userRepository.delete(user);
        userRepository.flush();
        assertThat(userRepository.findByUsername(user.getUsername())).isNull();
    }
}
