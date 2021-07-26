package extractor.service;

import extractor.entity.Transaction;
import extractor.entity.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    public long countSuccessfulTransactions(final List<Transaction> transactions) {
        final long successfulTransactions = transactions
                .stream()
                .filter(transaction -> TransactionStatus.SUCCESS.equals(transaction.getTransactionResult()) ||
                        TransactionStatus.COMPLETE.equals(transaction.getTransactionResult()))
                .count();
        logger.info("successful transactions: {}", successfulTransactions);
        return successfulTransactions;
    }

    public long countFailedTransactions(final List<Transaction> transactions) {
        final long failedTransactions = transactions
                .stream()
                .filter(transaction -> TransactionStatus.FAILED.equals(transaction.getTransactionResult()) ||
                        TransactionStatus.FAILURE.equals(transaction.getTransactionResult()))
                .count();
        logger.info("failed transactions: {}", failedTransactions);
        return failedTransactions;
    }

    public long countRejectedTransactions(final List<Transaction> transactions) {
        final long rejectedTransactions = transactions
                .stream()
                .filter(transaction -> TransactionStatus.REJECTED.equals(transaction.getTransactionResult()))
                .count();
        logger.info("rejected transactions: {}", rejectedTransactions);
        return rejectedTransactions;
    }

    public List<Transaction> getTop5Transactions(final List<Transaction> transactions) {
        final List<Transaction> top5Transactions = transactions
                .stream()
                .sorted()
                .limit(5)
                .collect(Collectors.toList());
        logger.info("top 5 transactions: {}", top5Transactions);
        return top5Transactions;
    }

    public int countSumOfFailedTransactions(final List<Transaction> transactions) {
        int sumOfFailedTransactions = 0;
        List<Transaction> listOfFailedTransactions = transactions
                .stream()
                .filter(transaction -> TransactionStatus.FAILED.equals(transaction.getTransactionResult()) ||
                        TransactionStatus.FAILURE.equals(transaction.getTransactionResult()))
                .collect(Collectors.toList());

        for (Transaction transaction : listOfFailedTransactions) {
            sumOfFailedTransactions += transaction.getAmount();
        }

        return sumOfFailedTransactions;
    }
}
