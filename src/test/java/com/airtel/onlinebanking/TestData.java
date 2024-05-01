package com.airtel.onlinebanking;

import com.airtel.onlinebanking.model.Account;
import com.airtel.onlinebanking.model.ScheduledTransaction;
import com.airtel.onlinebanking.model.Transaction;
import com.airtel.onlinebanking.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TestData {
    private User user;
    private Account account1;
    private Account account2;
    private Transaction transaction1;
    private Transaction transaction2;
    private ScheduledTransaction scheduledTransaction;

    public TestData() {
        user = new User();
        user.setFirstName("bbc");
        user.setLastName("dde");
        user.setPanNumber("9241290724");
        user.setAddress("abcVille");
        user.setDob(LocalDate.parse("2002-09-22"));
        user.setEmail("bbc@abc.com");
        user.setMobile(997241894L);
        user.setUsername("bbc");
        user.setPassword("123");
        account1 = new Account();
        account1.setCreatedDate(LocalDateTime.now());
        account1.setType("saving");
        account1.setBalance(10000000D);
        account2 = new Account();
        account2.setCreatedDate(LocalDateTime.now());
        account2.setType("business");
        account2.setBalance(1000000D);
        transaction1 = new Transaction();
        transaction1.setDescription("THIS IS FOR TESTING");
        transaction1.setTransferAccountId(1000000001L);
        transaction1.setType("debit");
        transaction1.setAmount(10000D);
        transaction1.setTimeStamp(LocalDateTime.now());
        transaction2 = new Transaction();
        transaction2.setDescription("THIS IS FOR TESTING");
        transaction2.setTransferAccountId(1000000000L);
        transaction2.setType("credit");
        transaction2.setAmount(10000D);
        transaction2.setTimeStamp(transaction1.getTimeStamp());
        scheduledTransaction = new ScheduledTransaction();
        scheduledTransaction.setDueDateTime(LocalDateTime.now());
        scheduledTransaction.setAmount(1D);
        scheduledTransaction.setCounter(5L);
        scheduledTransaction.setFromAccountId(1000000000L);
        scheduledTransaction.setToAccountId(1000000001L);
        scheduledTransaction.setMinuteIncrement(1L);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Account getAccount1() {
        return account1;
    }

    public void setAccount1(Account account1) {
        this.account1 = account1;
    }

    public Account getAccount2() {
        return account2;
    }

    public void setAccount2(Account account2) {
        this.account2 = account2;
    }

    public Transaction getTransaction1() {
        return transaction1;
    }

    public void setTransaction1(Transaction transaction1) {
        this.transaction1 = transaction1;
    }

    public Transaction getTransaction2() {
        return transaction2;
    }

    public void setTransaction2(Transaction transaction2) {
        this.transaction2 = transaction2;
    }

    public ScheduledTransaction getScheduledTransaction() {
        return scheduledTransaction;
    }

    public void setScheduledTransaction(ScheduledTransaction scheduledTransaction) {
        this.scheduledTransaction = scheduledTransaction;
    }
}
