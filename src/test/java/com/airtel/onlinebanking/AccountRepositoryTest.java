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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AccountRepositoryTest {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;
    private User user;
    private Account account;
    private Account account1;
    @BeforeEach
    public void setAccountAndUser() {
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
        account1 = new Account();
        account1.setCreatedDate(LocalDateTime.now());
        account1.setType("business");
        account1.setUser(user);
        account1.setBalance(1000000L);
        accountRepository.save(account1);
    }
    @AfterEach
    public void deleteAccountAndUser() {
        userRepository.delete(user);
        accountRepository.delete(account);
        accountRepository.delete(account1);
    }
    @Test
    @DisplayName("JUnit test to save account to valid user")
    public void shouldSaveAccount() {
        Account savedAccount = accountRepository.save(account);

        assertThat(savedAccount).isNotNull();
    }
    @Test
    @DisplayName("JUnit test to find accounts linked to user")
    public void shouldReturnAccountOfGivenType() {
        Account accounts = accountRepository.findByUserAndType(user, account.getType());

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
