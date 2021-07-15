package extractor.builder;

import extractor.entity.Transaction;
import extractor.entity.TransactionStatus;

import java.util.Date;

public class TransactionBuilder implements Builder {
    private Date date;
    private String transactionId;
    private String userId;
    private double amount;
    private String currency;
    private TransactionStatus status;

    @Override
    public TransactionBuilder setDate(Date date) {
        this.date = date;
        return this;
    }

    @Override
    public TransactionBuilder setTransactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    @Override
    public TransactionBuilder setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public TransactionBuilder setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    @Override
    public TransactionBuilder setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    @Override
    public TransactionBuilder setTransactionStatus(TransactionStatus status) {
        this.status = status;
        return this;
    }

    public Transaction getResult() {
        return new Transaction(date, transactionId, userId, amount, currency, status);
    }
}
