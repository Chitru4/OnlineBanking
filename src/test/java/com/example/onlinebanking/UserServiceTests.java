package com.example.onlinebanking;

import com.example.onlinebanking.model.User;
import com.example.onlinebanking.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class UserServiceTests {
    @Autowired
    private UserService userService;
    @Test
    void addUser_ShouldAddSuccessfully() {
        TestData testData = new TestData();
        User user = testData.getUser();
        userService.registerUser(user);
        assertAll(
                () -> assertNotNull(userService.findByUser("bbc"))
        );
    }
    @Test
    void deleteUser_ShouldDeleteSuccessfully() {
        userService.deleteUser("bbc");
        assertAll(
                () -> assertNull(userService.findByUser("bbc"))
        );
    }
}
