package extractor;

public interface FileParser {
    void parse(String filePath);
    void printAllTransactions();
    void printTopFiveTransactions();
    void printTotals();
}
