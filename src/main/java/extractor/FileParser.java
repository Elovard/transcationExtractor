package extractor;

import java.util.List;

public interface FileParser {
    void parse(String filePath);
    List<Transaction> printAllTransactions();
    void printTopFiveTransactions();
    void printTotals();
}
