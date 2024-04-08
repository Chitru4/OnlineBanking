package com.airtel.onlinebanking.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @SequenceGenerator(
            name = "transaction_sequence",
            sequenceName = "transaction_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "transaction_sequence"
    )
    private Long transactionId;
    private LocalDateTime timeStamp;
    private String accountType;
    private Long amount;
    private String fromUsername;
    private String toUsername;
    @Transient
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;
    public Transaction() {
    }

    public Transaction(LocalDateTime timeStamp, String accountType, Long amount, String fromUsername, String toUsername) {
        this.timeStamp = timeStamp;
        this.accountType = accountType;
        this.amount = amount;
        this.fromUsername = fromUsername;
        this.toUsername = toUsername;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String type) {
        this.accountType = type;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getFromUsername() {
        return fromUsername;
    }

    public void setFromUsername(String fromUsername) {
        this.fromUsername = fromUsername;
    }

    public String getToUsername() {
        return toUsername;
    }

    public void setToUsername(String toUsername) {
        this.toUsername = toUsername;
    }
}
