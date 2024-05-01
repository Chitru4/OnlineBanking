package com.airtel.onlinebanking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transaction", uniqueConstraints = {@UniqueConstraint(columnNames = {"transaction_id"})})
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
    @Column(nullable = false)
    private Long transactionId;
    private LocalDateTime timeStamp;
    private Double amount;
    private String type;
    private String description;
    private Long transferAccountId;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "account_account_id")
    private Account account;
    public Transaction() {
    }

    public Transaction(LocalDateTime timeStamp, Double amount, String type, String description, Long transferAccountId) {
        this.timeStamp = timeStamp;
        this.amount = amount;
        this.type = type;
        this.description = description;
        this.transferAccountId = transferAccountId;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Long getTransferAccountId() {
        return transferAccountId;
    }

    public void setTransferAccountId(Long transferAccountId) {
        this.transferAccountId = transferAccountId;
    }
}
