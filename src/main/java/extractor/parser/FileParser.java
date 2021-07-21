package extractor.parser;

import extractor.entity.Transaction;
import extractor.exception.TransactionFileParsingException;

import java.util.List;

public interface FileParser {
    List<Transaction> parse(String filePath) throws TransactionFileParsingException;
    String getSupportedFileType();
}
