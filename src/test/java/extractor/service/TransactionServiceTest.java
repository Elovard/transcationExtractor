package extractor.service;

import extractor.entity.Transaction;
import extractor.entity.TransactionStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @Test
    void whenNumberOfSuccessfulTransactionsIs5_ThenResultShouldBe5() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(new Date(15), "1", "1", 250, "usd", TransactionStatus.COMPLETE));
        transactions.add(new Transaction(new Date(15), "2", "2", 10, "usd", TransactionStatus.SUCCESS));
        transactions.add(new Transaction(new Date(15), "3", "3", 999, "usd", TransactionStatus.COMPLETE));
        transactions.add(new Transaction(new Date(15), "4", "4", 300, "usd", TransactionStatus.SUCCESS));
        transactions.add(new Transaction(new Date(15), "5", "5", 50, "usd", TransactionStatus.SUCCESS));
        long expectedResult = 5;
        long actualResult = transactionService.countSuccessfulTransactions(transactions);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void whenNumberOfFailedTransactionsIs5_ThenResultShouldBe5() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(new Date(15), "1", "1", 250, "usd", TransactionStatus.FAILED));
        transactions.add(new Transaction(new Date(15), "2", "2", 10, "usd", TransactionStatus.FAILED));
        transactions.add(new Transaction(new Date(15), "3", "3", 999, "usd", TransactionStatus.FAILED));
        transactions.add(new Transaction(new Date(15), "4", "4", 300, "usd", TransactionStatus.FAILURE));
        transactions.add(new Transaction(new Date(15), "5", "5", 50, "usd", TransactionStatus.FAILURE));
        long expectedResult = 5;
        long actualResult = transactionService.countFailedTransactions(transactions);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void whenNumberOfRejectedTransactionsIs5_ThenResultShouldBe5() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(new Date(15), "1", "1", 250, "usd", TransactionStatus.REJECTED));
        transactions.add(new Transaction(new Date(15), "2", "2", 10, "usd", TransactionStatus.REJECTED));
        transactions.add(new Transaction(new Date(15), "3", "3", 999, "usd", TransactionStatus.REJECTED));
        transactions.add(new Transaction(new Date(15), "4", "4", 300, "usd", TransactionStatus.REJECTED));
        transactions.add(new Transaction(new Date(15), "5", "5", 50, "usd", TransactionStatus.REJECTED));
        long expectedResult = 5;
        long actualResult = transactionService.countRejectedTransactions(transactions);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void whenListIsEmpty_ThenResultShouldBeZero() {
        List<Transaction> transactions = new ArrayList<>();
        long expectedResult = 0;
        long actualResult = transactionService.countSuccessfulTransactions(transactions);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void whenListContainsMoreThan5Transactions_ThenResultShouldBe5() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(new Date(15), "1", "1", 250, "usd", TransactionStatus.COMPLETE));
        transactions.add(new Transaction(new Date(15), "2", "2", 10, "usd", TransactionStatus.REJECTED));
        transactions.add(new Transaction(new Date(15), "3", "3", 999, "usd", TransactionStatus.SUCCESS));
        transactions.add(new Transaction(new Date(15), "4", "4", 300, "usd", TransactionStatus.FAILURE));
        transactions.add(new Transaction(new Date(15), "5", "5", 5100, "usd", TransactionStatus.REJECTED));
        transactions.add(new Transaction(new Date(15), "5", "5", 533, "usd", TransactionStatus.REJECTED));
        transactions.add(new Transaction(new Date(15), "5", "5", 791, "usd", TransactionStatus.REJECTED));
        long expectedResult = 5;
        List<Transaction> actualResult = transactionService.getTop5Transactions(transactions);
        assertEquals(expectedResult, actualResult.size());
    }

    @Test
    void whenListContainsLessThan5Transactions_ThenResultShouldBeEqualToListSize() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(new Date(15), "4", "4", 300, "usd", TransactionStatus.FAILURE));
        transactions.add(new Transaction(new Date(15), "5", "5", 5100, "usd", TransactionStatus.REJECTED));
        transactions.add(new Transaction(new Date(15), "5", "5", 533, "usd", TransactionStatus.REJECTED));
        long expectedResult = 3;
        List<Transaction> actualResult = transactionService.getTop5Transactions(transactions);
        assertEquals(expectedResult, actualResult.size());
    }

    @Test
    void whenResultOfTransactionIsNull_ThenItShouldNotBeCountedSuccessful() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(new Date(15), "2", "2", 10, "usd", null));
        transactions.add(new Transaction(new Date(15), "3", "3", 999, "usd", TransactionStatus.COMPLETE));
        transactions.add(new Transaction(new Date(15), "4", "4", 300, "usd", null));
        long expectedResult = 1;
        long actualResult = transactionService.countSuccessfulTransactions(transactions);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void whenResultOfTransactionIsNull_ThenItShouldNotBeCountedFailed() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(new Date(15), "3", "3", 999, "usd", TransactionStatus.FAILURE));
        transactions.add(new Transaction(new Date(15), "4", "4", 300, "usd", null));
        transactions.add(new Transaction(new Date(15), "2", "2", 10, "usd", null));
        long expectedResult = 1;
        long actualResult = transactionService.countFailedTransactions(transactions);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void whenResultOfTransactionIsNull_ThenItShouldNotBeCountedRejected() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(new Date(15), "4", "4", 300, "usd", null));
        transactions.add(new Transaction(new Date(15), "2", "2", 10, "usd", null));
        transactions.add(new Transaction(new Date(15), "3", "3", 999, "usd", TransactionStatus.REJECTED));
        long expectedResult = 1;
        long actualResult = transactionService.countRejectedTransactions(transactions);
        assertEquals(expectedResult, actualResult);
    }
}