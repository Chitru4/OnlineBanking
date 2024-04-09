package com.airtel.onlinebanking;


import com.airtel.onlinebanking.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@AutoConfigureMockMvc
public class RegisterTest {
    @LocalServerPort
    private int port;
    @Autowired
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;
    @Test
    void register() throws Exception {
        mockMvc.perform(post("http://localhost:" + port + "/register")
                        .param("firstName", "bbc")
                        .param("lastName", "dde")
                        .param("panNumber", "9241290724")
                        .param("address", "abcVille")
                        .param("dob", "2002-09-22")
                        .param("email", "bbc@abc.com")
                        .param("mobile", "997241894")
                        .param("username", "bbc")
                        .param("password", "123")
                )
                .andExpect(status().isOk());
        assertAll(
                () -> assertNotNull(userService.findByUser("bbc"))
        );
    }
}
