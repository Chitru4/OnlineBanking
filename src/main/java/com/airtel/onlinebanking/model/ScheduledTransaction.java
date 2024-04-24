package com.airtel.onlinebanking.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="scheduled_transaction")
public class ScheduledTransaction {
    @Id
    @SequenceGenerator(
            name = "scheduled_transaction_sequence",
            sequenceName = "scheduled_transaction_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "scheduled_transaction_sequence"
    )
    private Long id;
    private Long fromAccountId;
    private Long toAccountId;
    private Double amount;
    private LocalDateTime dueDateTime;
    private Long minuteIncrement;
    private Long counter;

    public ScheduledTransaction() {
    }

    public ScheduledTransaction(Long id, Long fromAccountId, Long toAccountId, LocalDateTime dueDateTime, Long minuteIncrement, Double amount, Long counter) {
        this.id = id;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.dueDateTime = dueDateTime;
        this.minuteIncrement = minuteIncrement;
        this.amount = amount;
        this.counter = counter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(Long fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public Long getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(Long toAccountId) {
        this.toAccountId = toAccountId;
    }

    public LocalDateTime getDueDateTime() {
        return dueDateTime;
    }

    public void setDueDateTime(LocalDateTime dueDateTime) {
        this.dueDateTime = dueDateTime;
    }

    public Long getMinuteIncrement() {
        return minuteIncrement;
    }

    public void setMinuteIncrement(Long minuteIncrement) {
        this.minuteIncrement = minuteIncrement;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getCounter() {
        return counter;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }
}
