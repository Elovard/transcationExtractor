package extractor;

import java.util.Date;

public class Transaction implements Comparable<Transaction> {

    private Date timestamp;
    private String transactionId;
    private String userId;
    private double amount;
    private String currency;
    private TransactionStatus transactionResult;

    public Transaction(Date timestamp,
                       String transactionId,
                       String userId,
                       double amount,
                       String currency,
                       TransactionStatus transactionResult) {

        this.timestamp = timestamp;
        this.transactionId = transactionId;
        this.userId = userId;
        this.amount = amount;
        this.currency = currency;
        this.transactionResult = transactionResult;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getUserId() {
        return userId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public TransactionStatus getTransactionResult() {
        return transactionResult;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", userId='" + userId + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", amount='" + amount + '\'' +
                ", currency='" + currency + '\'' +
                ", transactionResult='" + transactionResult + '\'' +
                '}';
    }


    @Override
    public int compareTo(Transaction transaction) {
        int amount1 = (int) this.getAmount();
        int amount2 = (int) transaction.getAmount();

        if (amount1 > amount2) {
            return 1;
        }
        if (amount1 < amount2) {
            return -1;
        }
        return 0;
    }
}
