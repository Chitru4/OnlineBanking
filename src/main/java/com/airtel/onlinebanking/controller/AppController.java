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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

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
    // Get mappings
    @RequestMapping(value = "")
    public String index() {
        return "index";
    }
    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }
    @RequestMapping(value = "/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    @RequestMapping(value = "/transaction")
    public String transaction(Principal principal, Model model) {
        Long accountId = 0L;
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("accountId", accountId);
        model.addAttribute("accountOptions", accountService.getAllAccounts(principal.getName()));
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
    public String viewPastTransactions(Principal principal, Model model) {
        model.addAttribute("transactions", transactionService.getTransactions(principal.getName()));
        return "past-transactions";
    }
    // Post mappings
    @PostMapping(value = "/register")
    public String registerUser(User user) {
        if (userService.registerUser(user) != null) {
            return "register-success";
        }
        else {
            return "error/500";
        }
    }
    @PostMapping(value = "/create-account")
    public String createAccount(Principal principal, Account account, RedirectAttributes redirectAttributes) {
        if (accountService.createAccount(principal.getName(), account) != null) {
            redirectAttributes.addFlashAttribute("success", "Account created successfully");
        }
        else {
            redirectAttributes.addFlashAttribute("error", "Account type already present");
        }
        return "redirect:/create-account";
    }
    @PostMapping(value = "/transaction")
    public String doTransaction(Principal principal, Transaction transaction, Long accountId, RedirectAttributes redirectAttributes) {
        transaction.setAccount(accountService.getByAccountId(accountId));
        switch (transactionService.doTransaction(principal.getName(), transaction)) {
            case 1 -> { return "transaction-success"; }
            case -1 -> {
                redirectAttributes.addFlashAttribute("error", "Low account balance");
                return "redirect:/transaction";
            }
            case -2 -> {
                redirectAttributes.addFlashAttribute("error", "Incorrect pin");
                return "redirect:/transaction";
            }
            case -3 -> {
                redirectAttributes.addFlashAttribute("error","Daily transaction amount limit reached");
                return "redirect:/transaction";
            }
            case 0 -> {
                redirectAttributes.addFlashAttribute("error", "User account does not exist");
                return "redirect:/transaction";
            }
            default -> { return "error/500"; }
        }
    }
}
