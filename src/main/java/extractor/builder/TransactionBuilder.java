package extractor.builder;

import extractor.entity.Transaction;
import extractor.entity.TransactionStatus;

import java.util.Date;

public class TransactionBuilder {
    private Date date;
    private String transactionId;
    private String userId;
    private double amount;
    private String currency;
    private TransactionStatus status;

    public TransactionBuilder setDate(Date date) {
        this.date = date;
        return this;
    }

    public TransactionBuilder setTransactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public TransactionBuilder setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public TransactionBuilder setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public TransactionBuilder setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public TransactionBuilder setTransactionStatus(TransactionStatus status) {
        this.status = status;
        return this;
    }

    public Transaction build() {
        return new Transaction(date, transactionId, userId, amount, currency, status);
    }
}
