package com.airtel.onlinebanking.controller;

import com.airtel.onlinebanking.model.User;
import com.airtel.onlinebanking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping(value = "")
public class RestController {
    private final UserService userService;
    @Autowired
    RestController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping(value = "/users")
    public List<User> showAllUsers() {
        return userService.showAllUsers();
    }
}
