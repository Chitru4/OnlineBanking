package com.airtel.onlinebanking;

import com.airtel.onlinebanking.model.Account;
import com.airtel.onlinebanking.model.Transaction;
import com.airtel.onlinebanking.model.User;
import com.airtel.onlinebanking.repository.AccountRepository;
import com.airtel.onlinebanking.repository.TransactionRepository;
import com.airtel.onlinebanking.repository.UserRepository;
import com.airtel.onlinebanking.service.AccountService;
import com.airtel.onlinebanking.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@WithMockUser(username = "bbc")
@AutoConfigureMockMvc
public class TransactionServiceTests {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private MockMvc mockMvc;
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
        user.setUsername("dbc");
        user.setPassword("123");
        userRepository.save(user);
        account = new Account();
        account.setUser(user);
        account.setType("saving");
        account.setBalance(1000000L);
        account.setCreatedDate(LocalDateTime.now());
        accountRepository.save(account);
        transaction = new Transaction();
        transaction.setAccountType("saving");
        transaction.setAmount(10000L);
        transaction.setTimeStamp(LocalDateTime.now());
        transaction.setFromUsername(user.getUsername());
        transaction.setToUsername(user.getUsername());
    }
    @Test
    void doTransaction_ShouldCompleteSuccessfully() {
        assertThat((transactionService.doTransaction(user.getUsername(), transaction))).isEqualTo(1);
    }
}
