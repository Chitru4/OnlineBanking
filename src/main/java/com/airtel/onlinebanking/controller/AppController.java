package com.airtel.onlinebanking.controller;

import com.airtel.onlinebanking.model.Account;
import com.airtel.onlinebanking.model.ScheduledTransaction;
import com.airtel.onlinebanking.model.Transaction;
import com.airtel.onlinebanking.model.User;
import com.airtel.onlinebanking.service.AccountService;
import com.airtel.onlinebanking.service.ScheduledTransactionService;
import com.airtel.onlinebanking.service.TransactionService;
import com.airtel.onlinebanking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final AccountService accountService;
    private final TransactionService transactionService;
    private final ScheduledTransactionService scheduledTransactionService;
    @Autowired
    public AppController(UserService userService, AccountService accountService, TransactionService transactionService, ScheduledTransactionService scheduledTransactionService) {
        this.userService = userService;
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.scheduledTransactionService = scheduledTransactionService;
    }
    /**
     * GET MAPPINGS
     **/
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
        Integer pin = 0;
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("accountId", accountId);
        model.addAttribute("pin", pin);
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
        model.addAttribute("debitTransactions", transactionService.getTransactions(principal.getName()));
        model.addAttribute("creditTransactions", transactionService.getTransactionsByTransferAccountId(principal.getName()));
        return "past-transactions";
    }
    @RequestMapping(value="/fund-transfer")
    public String fundTransfer(Principal principal, Model model) {
        Long accountId = 0L;
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("accountId", accountId);
        model.addAttribute("accountOptions", accountService.getAllAccounts(principal.getName()));
        return "fund-transfer";
    }
    @RequestMapping(value = "/manage-user")
    public String manageAccount(Principal principal, Model model) {
        model.addAttribute("user", userService.findByUser(principal.getName()));
        return "manage-user";
    }
    @RequestMapping(value="/setup-auto-pay")
    public String setupScheduledTransaction(Principal principal, Model model) {
        model.addAttribute("scheduledTransaction", new ScheduledTransaction());
        model.addAttribute("accountOptions", accountService.getAllAccounts(principal.getName()));
        return "setup-auto-pay";
    }
    @RequestMapping(value="/manage-auto-pay")
    public String showScheduledTransaction(Principal principal, Model model) {
        model.addAttribute("scheduledTransactions", scheduledTransactionService.getAllUserScheduledTransactions(principal.getName()));
        return "manage-auto-pay";
    }
    /**
     * POST MAPPINGS
     **/
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
    public String doTransaction(Principal principal, Transaction transaction, Long accountId, Integer pin, RedirectAttributes redirectAttributes) {
        transaction.setAccount(accountService.getByAccountId(accountId));
        if (!(transaction.getAccount().getPin() == pin)) {
            redirectAttributes.addFlashAttribute("error", "Incorrect pin");
            return "redirect:/transaction";
        }
        switch (transactionService.doTransaction(principal.getName(), transaction, "bank")) {
            case 1 -> { return "transaction-success"; }
            case -1 -> {
                redirectAttributes.addFlashAttribute("error", "Low account balance");
                return "redirect:/transaction";
            }
            case -2 -> {
                redirectAttributes.addFlashAttribute("error", "Cannot transfer to same user (Use fund transfer instead)");
                return "redirect:/fund-transfer";
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

    @PostMapping(value = "/fund-transfer")
    public String doFundTransaction(Principal principal, Transaction transaction, Long accountId, RedirectAttributes redirectAttributes) {
        transaction.setAccount(accountService.getByAccountId(accountId));
        switch (transactionService.doTransaction(principal.getName(), transaction, "fund")) {
            case 1 -> { return "transaction-success"; }
            case -1 -> {
                redirectAttributes.addFlashAttribute("error", "Low account balance");
                return "redirect:/fund-transfer";
            }
            case 0 -> {
                redirectAttributes.addFlashAttribute("error", "User account does not exist/ same account");
                return "redirect:/fund-transfer";
            }
            default -> { return "error/500"; }
        }
    }
    @PostMapping(value = "/manage-user")
    public String changeUserDetails(Principal principal,User user, RedirectAttributes redirectAttributes) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User newUser = userService.findByUser(principal.getName());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setMobile(user.getMobile());
        newUser.setAddress(user.getAddress());
        if (!passwordEncoder.matches(user.getPassword(), newUser.getPassword())) {
            redirectAttributes.addFlashAttribute("error", "Wrong Password");
            return "redirect:/manage-user";
        }
        userService.repositorySave(newUser);
        return "index";
    }
    @PostMapping(value = "/setup-auto-pay")
    public String setupScheduledTransaction(Principal principal, ScheduledTransaction scheduledTransaction, RedirectAttributes redirectAttributes) {
        scheduledTransactionService.saveScheduledTransaction(scheduledTransaction);
        return "index";
    }
}
