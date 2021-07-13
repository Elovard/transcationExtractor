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
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public void setTransactionStatus(TransactionStatus status) {
        this.status = status;
    }

    public Transaction getResult() {
        return new Transaction(date, transactionId, userId, amount, currency, status);
    }
}
