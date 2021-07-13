package extractor.builder;

import extractor.entity.TransactionStatus;

import java.util.Date;

public interface Builder {
    void setDate(Date date);

    void setTransactionId(String transactionId);

    void setUserId(String userId);

    void setAmount(double amount);

    void setCurrency(String currency);

    void setTransactionStatus(TransactionStatus status);

}
