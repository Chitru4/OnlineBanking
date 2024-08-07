package com.example.onlinebanking;

import com.example.onlinebanking.model.User;
import com.example.onlinebanking.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    private User user;
    @BeforeEach
    public void setupUserData() {
        TestData testData = new TestData();
        user = testData.getUser();
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
        assertThat(savedUser.getUsername()).isEqualTo(user.getUsername());
    }
    @Test
    @DisplayName("JUnit test for deleting user by username")
    public void shouldDeleteUser() {
        userRepository.deleteByUsername(user.getUsername());
        User deleteEmployee = userRepository.findByUsername(user.getUsername());

        assertThat(deleteEmployee).isNull();
    }
}
