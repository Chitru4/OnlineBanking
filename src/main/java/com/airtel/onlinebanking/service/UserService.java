package com.airtel.onlinebanking.service;

import com.airtel.onlinebanking.model.User;
import com.airtel.onlinebanking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private final UserRepository userRepository;
    @Autowired
    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User registerUser(User user) {
        if (user.getUsername() == null || userRepository.findByUsername(user.getUsername()) != null) {
            return null;
        }
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        String encodedPanNumber = encoder.encode(user.getPanNumber());
        user.setPanNumber(encodedPanNumber);

        userRepository.save(user);
        return user;
    }
    public User findByUser(String username) { return userRepository.findByUsername(username); }

    public void deleteUser(String username) {
        userRepository.deleteByUsername(username);
    }

    public List<User> showAllUsers() {
        return userRepository.findAll();
    }
}
