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

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private Transaction transaction;
    @BeforeEach
    public void setUpUserAccountTransaction() {
        user = new User();
        user.setFirstName("qbc");
        user.setLastName("dde");
        user.setPanNumber("9241290724");
        user.setAddress("abcVille");
        user.setDob(LocalDate.parse("2002-09-22"));
        user.setEmail("bbc@abc.com");
        user.setMobile(997241894L);
        user.setUsername("qbc");
        user.setPassword("123");
        userRepository.save(user);
        account = new Account();
        account.setCreatedDate(LocalDateTime.now());
        account.setType("saving");
        account.setUser(user);
        account.setBalance(10000000L);
        accountRepository.save(account);
        transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setFromUsername(user.getUsername());
        transaction.setToUsername(user.getUsername());
        transaction.setAmount(10000L);
        transaction.setAccountType("saving");
        transaction.setTimeStamp(LocalDateTime.now());
    }
    @AfterEach
    public void deleteUserAccountTransaction() {
        userRepository.delete(user);
        accountRepository.delete(account);
        transactionRepository.delete(transaction);
    }
    @Test
    @DisplayName("JUnit test to find transactions made from given user")
    public void shouldReturnListOfTransactions() {
        List<Transaction> savedTransactions = transactionRepository.findByFromUsername(user.getUsername());

        assertThat(savedTransactions).isNotNull();
    }
}
