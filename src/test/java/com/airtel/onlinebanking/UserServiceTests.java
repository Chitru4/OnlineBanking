package com.airtel.onlinebanking;

import com.airtel.onlinebanking.model.User;
import com.airtel.onlinebanking.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class UserServiceTests {
    @Autowired
    private UserService userService;
    @Test
    void addUser_ShouldAddSuccessfully() {
        if (userService.findByUser("bbc")!=null) {
            deleteUser_ShouldDeleteSuccessfully();
        }
        User user = new User();
        user.setFirstName("bbc");
        user.setLastName("dde");
        user.setPanNumber("9241290724");
        user.setAddress("abcVille");
        user.setDob(LocalDate.parse("2002-09-22"));
        user.setEmail("bbc@abc.com");
        user.setMobile(997241894L);
        user.setUsername("cbc");
        user.setPassword("123");
        userService.registerUser(user);
        assertAll(
                () -> assertNotNull(userService.findByUser("cbc"))
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
