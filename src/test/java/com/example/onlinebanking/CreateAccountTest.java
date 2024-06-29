package com.example.onlinebanking;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@AutoConfigureMockMvc
@WithMockUser(username = "bbc")
public class CreateAccountTest {
    @LocalServerPort
    private int port;
    @Autowired
    private MockMvc mockMvc;
    @Test
    @DisplayName("JUnit test for testing /create-account")
    public void createAccount_ShouldCreateAccount() throws Exception {
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

        mockMvc.perform(post("http://localhost:" + port + "/create-account")
                .param("type","saving")
                .param("balance", "100000")
                )
                .andExpect(redirectedUrl("/create-account"));

        assertTrue(mockMvc.perform(get("http://localhost" + port + "/accounts")
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
                .contains("bbc"));
    }
}
