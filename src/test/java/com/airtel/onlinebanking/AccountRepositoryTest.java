package com.airtel.onlinebanking;

import com.airtel.onlinebanking.model.Account;
import com.airtel.onlinebanking.model.User;
import com.airtel.onlinebanking.repository.AccountRepository;
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
public class AccountRepositoryTest {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;
    private User user;
    private Account account1;
    private Account account2;

    @BeforeEach
    public void setAccountAndUser() {
        TestData testData = new TestData();
        user = testData.getUser();
        userRepository.saveAndFlush(user);
        account1 = testData.getAccount1();
        account1.setUser(user);
        accountRepository.saveAndFlush(account1);
        account2 = testData.getAccount2();
        account2.setUser(user);
        accountRepository.saveAndFlush(account2);
    }
    @AfterEach
    public void deleteAccountAndUser() {
        userRepository.delete(user);
        accountRepository.delete(account1);
        accountRepository.delete(account2);
    }
    @Test
    @DisplayName("JUnit test to save account to valid user")
    public void shouldSaveAccount() {
        Account savedAccount = accountRepository.save(account1);

        assertThat(savedAccount).isNotNull();
    }
    @Test
    @DisplayName("JUnit test to find accounts linked to user")
    public void shouldReturnAccountOfGivenType() {
        Account accounts = accountRepository.findByAccountId(account1.getAccountId());

        assertThat(accounts).isNotNull();
    }
    @Test
    @DisplayName("JUnit test to find accounts linked to user")
    public void shouldReturnListOfAccounts() {
        List<Account> accounts = accountRepository.findByUser(user);

        assertThat(accounts).isNotNull();
        assertThat(accounts.size()).isEqualTo(2);
    }
}
