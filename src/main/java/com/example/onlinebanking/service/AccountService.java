package com.example.onlinebanking.service;

import com.example.onlinebanking.model.Account;
import com.example.onlinebanking.model.User;
import com.example.onlinebanking.repository.AccountRepository;
import com.example.onlinebanking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    @Autowired
    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }
    public Account createAccount(String username, Account account) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }
        account.setUser(user);
        account.setCreatedDate(LocalDateTime.now());
        accountRepository.save(account);
        return account;
    }
    public List<Account> getAllAccounts(String username) {
        return accountRepository.findByUser(userRepository.findByUsername(username));
    }
    public Account getByAccountId(Long accountId) {
        return accountRepository.findByAccountId(accountId);
    }
}
