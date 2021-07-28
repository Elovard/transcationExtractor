package extractor.parser;

import extractor.entity.Transaction;
import extractor.exception.TransactionFileParsingException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface FileParser {
    List<Transaction> parse(String filePath) throws TransactionFileParsingException;
    String getSupportedFileType();
}
