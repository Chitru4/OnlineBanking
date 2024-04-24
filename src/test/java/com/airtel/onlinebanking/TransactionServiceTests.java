package com.airtel.onlinebanking;

import com.airtel.onlinebanking.model.Account;
import com.airtel.onlinebanking.model.Transaction;
import com.airtel.onlinebanking.model.User;
import com.airtel.onlinebanking.repository.AccountRepository;
import com.airtel.onlinebanking.repository.UserRepository;
import com.airtel.onlinebanking.service.TransactionService;
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
        userRepository.save(user);
        account1 = testData.getAccount1();
        accountRepository.save(account1);
        account2 = testData.getAccount2();
        accountRepository.save(account2);
        transaction = testData.getTransaction1();
    }
    @Test
    void doTransaction_ShouldCompleteSuccessfully() {
        assertThat((transactionService.doTransaction(user.getUsername(), transaction, "-fund"))).isEqualTo(1);
    }
}
