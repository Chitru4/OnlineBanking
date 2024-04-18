package com.airtel.onlinebanking;

import com.airtel.onlinebanking.model.Account;
import com.airtel.onlinebanking.model.Transaction;
import com.airtel.onlinebanking.model.User;
import com.airtel.onlinebanking.repository.AccountRepository;
import com.airtel.onlinebanking.repository.TransactionRepository;
import com.airtel.onlinebanking.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TransactionRepositoryTest {
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

    @BeforeEach
    public void setUpUserAccountTransaction() {
        TestData testData = new TestData();
        user = testData.getUser();
        userRepository.save(user);
        account = testData.getAccount1();
        accountRepository.save(account);
        transaction1 = testData.getTransaction1();
        transactionRepository.save(transaction1);
        transaction2 = testData.getTransaction2();
        transactionRepository.save(transaction2);
    }
    @AfterEach
    public void deleteUserAccountTransaction() {
        userRepository.delete(user);
        accountRepository.delete(account);
        transactionRepository.delete(transaction1);
        transactionRepository.delete(transaction2);
    }
    @Test
    @DisplayName("JUnit test to find transactions made from given account")
    public void shouldReturnListOfTransactions() {
        List<Transaction> savedTransactions = transactionRepository.findByAccount(account);

        assertThat(savedTransactions).isNotNull();
    }
}
