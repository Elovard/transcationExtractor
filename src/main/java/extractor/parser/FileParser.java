package extractor.parser;

import extractor.entity.Transaction;

import java.util.List;

public interface FileParser {
    List<Transaction> parse(String filePath);
    List<Transaction> printAllTransactions();
    List<Transaction> printTopFiveTransactions();
    List<Transaction> printTotals();

    String getSupportedFileType();
}
