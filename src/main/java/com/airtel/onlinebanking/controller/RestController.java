package com.airtel.onlinebanking.controller;

import com.airtel.onlinebanking.model.User;
import com.airtel.onlinebanking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping(value = "api")
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

    @GetMapping(value = "/joke-api")
    public ResponseEntity<String> showRandomJoke() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity("https://v2.jokeapi.dev/joke/Programming?blacklistFlags=nsfw,religious,political,racist,sexist,explicit&type=single", String.class);
//        return restTemplate.getForEntity("https://v2.jokeapi.dev/joke/Any", String.class);
    }
}
