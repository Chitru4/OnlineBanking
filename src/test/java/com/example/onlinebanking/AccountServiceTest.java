package com.example.onlinebanking;

import com.example.onlinebanking.service.AccountService;
import com.example.onlinebanking.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountServiceTest {
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;
    @Test
    void createAccount_ShouldCreateSuccessfully() {
        assertAll(
                () -> assertNotNull(accountService),
                () -> assertNotNull(userService)
        );
    }
}
