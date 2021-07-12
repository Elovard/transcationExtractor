package extractor;

public class Transaction implements Comparable<Transaction> {

    private String timestamp;
    private String transactionId;
    private String userId;
    private String amount;
    private String currency;
    private TransactionStatus transactionResult;

    public Transaction(String timestamp, // data
                       String transactionId,
                       String userId,// uuid
                       String amount, // double
                       String currency,
                       TransactionStatus transactionResult /*enum*/) {

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

    public String getTimestamp() {
        return timestamp;
    }

    public String getAmount() {
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
