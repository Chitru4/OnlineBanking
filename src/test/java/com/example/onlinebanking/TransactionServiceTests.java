package com.example.onlinebanking;

import com.example.onlinebanking.model.Account;
import com.example.onlinebanking.model.Transaction;
import com.example.onlinebanking.model.User;
import com.example.onlinebanking.repository.AccountRepository;
import com.example.onlinebanking.repository.UserRepository;
import com.example.onlinebanking.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class TransactionServiceTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionService transactionService;
    private User user;
    private Account account1;
    private Account account2;
    private Transaction transaction;

    @BeforeEach
    public void setupUserData() {
        TestData testData = new TestData();
        user = testData.getUser();
        userRepository.saveAndFlush(user);
        account1 = testData.getAccount1();
        account1.setUser(user);
        accountRepository.saveAndFlush(account1);
        account2 = testData.getAccount2();
        account2.setUser(user);
        accountRepository.saveAndFlush(account2);
        transaction = testData.getTransaction1();
        transaction.setAccount(account1);
    }
    @Test
    void doTransaction_ShouldCompleteSuccessfully() {
        assertThat((transactionService.doTransaction(user.getUsername(), transaction, "-fund"))).isEqualTo(1);
    }
}
