package extractor.parser;

import extractor.entity.Transaction;

import java.util.List;

public interface FileParser {
    List<Transaction> parse(String filePath) throws Exception;
    String getSupportedFileType();
}
