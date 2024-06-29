package com.example.onlinebanking;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@WithMockUser(username = "bbc")
public class MakeTransactionTest {
    @LocalServerPort
    private int port;
    @Autowired
    private MockMvc mockMvc;
    @Test
    @DisplayName("Integration test testing routine from register to account creation to transaction")
    public void doTransaction_ShouldSuccessfullyCompleteTransaction() throws Exception {
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
                        .param("pin","1234")
                )
                .andExpect(redirectedUrl("/create-account"));
        mockMvc.perform(post("http://localhost:" + port + "/create-account")
                        .param("type","saving")
                        .param("balance", "100000")
                        .param("pin","1234")
                )
                .andExpect(redirectedUrl("/create-account"));

        assertTrue(mockMvc.perform(get("http://localhost" + port + "/accounts")
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
                .contains("bbc"));

        mockMvc.perform(post("http://localhost" + port + "/fund-transfer")
                    .param("description","THIS IS FOR TESTING")
                    .param("amount","1000")
                    .param("transferAccountId","1000000001")
                    .param("accountId","1000000000")
                )
                .andExpect(status().isOk());

        assertTrue(mockMvc.perform(get("http://localhost" + port + "/past-transactions")
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
                .contains("1000000000"));
    }
}
