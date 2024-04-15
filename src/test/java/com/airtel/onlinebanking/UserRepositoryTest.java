package com.airtel.onlinebanking;

import com.airtel.onlinebanking.model.User;
import com.airtel.onlinebanking.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    private User user;
    @BeforeEach
    public void setupUserData() {
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

    }
    @AfterEach
    public void deleteUserData() {
        userRepository.delete(user);
    }
    @Test
    @DisplayName("JUnit test for saving user operation")
    public void shouldAddUser() {
        User savedUser = userRepository.save(user);

        assertThat(savedUser).isNotNull();
    }
    @Test
    @DisplayName("JUnit test for finding user by username")
    public void shouldReturnUser() {
        User savedUser = userRepository.findByUsername(user.getUsername());

        assertThat(savedUser).isNotNull();
        assertThat(savedUser).isEqualTo(user);
    }
    @Test
    @DisplayName("JUnit test for deleting user by username")
    public void shouldDeleteUser() {
        userRepository.deleteByUsername(user.getUsername());
        User deleteEmployee = userRepository.findByUsername(user.getUsername());

        assertThat(deleteEmployee).isNull();
    }
}
