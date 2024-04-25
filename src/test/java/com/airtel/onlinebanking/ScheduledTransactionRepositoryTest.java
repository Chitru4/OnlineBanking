package com.airtel.onlinebanking;

import com.airtel.onlinebanking.model.ScheduledTransaction;
import com.airtel.onlinebanking.repository.ScheduledTransactionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ScheduledTransactionRepositoryTest {
    @Autowired
    private ScheduledTransactionRepository scheduledTransactionRepository;
    private ScheduledTransaction scheduledTransaction;

    @BeforeEach
    public void setUpUserAccountTransaction() {
        TestData testData = new TestData();
        scheduledTransaction = testData.getScheduledTransaction();
    }
    @AfterEach
    public void deleteUserAccountTransaction() {
        scheduledTransactionRepository.delete(scheduledTransaction);
    }
    @Test
    @DisplayName("JUnit test for saving scheduled transaction operation")
    public void shouldAddScheduledTransaction() {
        ScheduledTransaction savedScheduledTransaction = scheduledTransactionRepository.save(scheduledTransaction);

        assertThat(savedScheduledTransaction).isNotNull();
    }
}
