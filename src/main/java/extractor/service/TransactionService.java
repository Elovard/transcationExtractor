package extractor.service;

import extractor.entity.Transaction;
import extractor.entity.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    private static final TransactionService INSTANCE = new TransactionService();

    private TransactionService() {

    }

    public static TransactionService getInstance() {
        return INSTANCE;
    }

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
}
