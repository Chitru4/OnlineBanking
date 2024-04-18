package com.airtel.onlinebanking.service;

import com.airtel.onlinebanking.model.Account;
import com.airtel.onlinebanking.model.User;
import com.airtel.onlinebanking.repository.AccountRepository;
import com.airtel.onlinebanking.repository.UserRepository;
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
