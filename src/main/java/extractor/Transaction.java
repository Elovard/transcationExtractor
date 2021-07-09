package extractor;

public class Transaction implements Comparable<Transaction> {

    private String timestamp;
    private String transactionId;
    private String userId;
    private String amount;
    private String currency;
    private String transactionResult;

    public Transaction(String transactionId,
                       String userId,
                       String timestamp,
                       String amount,
                       String currency,
                       String transactionResult) {

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

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTransactionResult() {
        return transactionResult;
    }

    public void setTransactionResult(String transactionResult) {
        this.transactionResult = transactionResult;
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
        int amount1 = Integer.parseInt(this.getAmount());
        int amount2 = Integer.parseInt(transaction.getAmount());

        if (amount1 > amount2) {
            return 1;
        }
        if (amount1 < amount2) {
            return -1;
        }
        return 0;
    }
}
