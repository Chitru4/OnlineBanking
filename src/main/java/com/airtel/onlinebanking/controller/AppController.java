package com.airtel.onlinebanking.controller;

import com.airtel.onlinebanking.model.Account;
import com.airtel.onlinebanking.model.Transaction;
import com.airtel.onlinebanking.model.User;
import com.airtel.onlinebanking.service.AccountService;
import com.airtel.onlinebanking.service.TransactionService;
import com.airtel.onlinebanking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class AppController {
    private final UserService userService;
    private final TransactionService transactionService;
    private final AccountService accountService;
    @Autowired
    public AppController(UserService userService, TransactionService transactionService, AccountService accountService) {
        this.userService = userService;
        this.transactionService = transactionService;
        this.accountService = accountService;
    }
    @RequestMapping(value = "")
    public String index() {
        return "index";
    }
    @RequestMapping(value = "/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    @RequestMapping(value = "/transaction")
    public String transaction(Model model) {
        model.addAttribute("transaction", new Transaction());
        return "transaction";
    }
    @RequestMapping(value = "/accounts")
    public String getAccounts(Principal principal, Model model) {
        model.addAttribute("accounts", accountService.getAllAccounts(principal.getName()));
        return "accounts";
    }
    @RequestMapping(value = "/create-account")
    public String createAccount(Model model) {
        model.addAttribute("account", new Account());
        return "create-account";
    }
    @RequestMapping(value = "/past-transactions")
    public String viewPastTransactions(Model model) {
        model.addAttribute("account", new Account());
        return "create-account";
    }
    @PostMapping(value = "/register")
    public String registerUser(User user) {
        if (userService.registerUser(user) != null) {
            return "register-success";
        }
        else {
            return "error";
        }
    }
    @PostMapping(value = "/create-account")
    public String createAccount(Principal principal, Account account) {
        if (accountService.createAccount(principal.getName(), account) != null) {
            return "transaction-success";
        }
        else {
            return "error";
        }
    }
    @PostMapping(value = "/transaction")
    public String doTransaction(Principal principal, Transaction transaction) {
        int errorCode = transactionService.doTransaction(principal.getName(), transaction);
        System.out.println(errorCode);
        if (errorCode == 1) {
            return "transaction-success";
        }
        else {
            System.out.println();
            return "error";
        }
    }
}
