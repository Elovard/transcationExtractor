package extractor.builder;

import extractor.entity.TransactionStatus;

import java.util.Date;

public interface Builder {
    TransactionBuilder setDate(Date date);

    TransactionBuilder setTransactionId(String transactionId);

    TransactionBuilder setUserId(String userId);

    TransactionBuilder setAmount(double amount);

    TransactionBuilder setCurrency(String currency);

    TransactionBuilder setTransactionStatus(TransactionStatus status);

}
