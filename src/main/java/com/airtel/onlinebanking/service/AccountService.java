package com.airtel.onlinebanking.service;

import com.airtel.onlinebanking.model.Account;
import com.airtel.onlinebanking.model.User;
import com.airtel.onlinebanking.repository.AccountRepository;
import com.airtel.onlinebanking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        String accountType = account.getType();
        if (user.getAccounts() != null) {
            for (Account userAccount : user.getAccounts()) {
                if (userAccount.getType().equals(accountType)) {
                    return null;
                }
            }
        }
        account.setUser(user);
        accountRepository.save(account);
        return account;
    }
    public List<Account> getAllAccounts(String username) {
        return accountRepository.findByUser(userRepository.findByUsername(username));
    }

}
