package extractor.parser;

import extractor.entity.Transaction;
import extractor.exception.TransactionException;

import java.util.List;

public interface FileParser {
    List<Transaction> parse(String filePath) throws TransactionException;
    String getSupportedFileType();
}
