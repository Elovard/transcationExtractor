package extractor.parser;

import extractor.builder.TransactionBuilder;
import extractor.entity.Transaction;
import extractor.entity.TransactionStatus;
import extractor.exception.TransactionFileParsingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatParser implements FileParser {

    private static final List<Transaction> listOfTransactions = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(CsvParser.class);

    @Override
    public List<Transaction> parse(String filePath) throws TransactionFileParsingException {
        logger.info("entering parse method in DatParser");
        Path path = Paths.get(filePath);
        try {
            List<String> rows = Files.readAllLines(path);
            rows.remove(0);

            for (String row : rows) {
                listOfTransactions.add(parseOneLine(row));
            }
        } catch (IOException ex) {
            logger.error("Can't find this file");
            throw new TransactionFileParsingException("Can't find your file!");
        }
        logger.info("parsed successfully");
        return listOfTransactions;
    }

    public Transaction parseOneLine(String line) {
        logger.info("parsing by lines in DatParser");
        String[] parsedData = line.split(",");

        long parsedLong = Long.parseLong(parsedData[0]);
        Timestamp timestamp = new Timestamp(parsedLong);
        Date date = new Date(timestamp.getTime());

        logger.info("creating new transaction object");
        return new TransactionBuilder()
                .setDate(date)
                .setTransactionId(parsedData[1])
                .setUserId(parsedData[2])
                .setAmount(Double.parseDouble(parsedData[3]))
                .setCurrency(parsedData[4])
                .setTransactionStatus(TransactionStatus.valueOf(parsedData[5].toUpperCase()))
                .build();
    }

    @Override
    public String getSupportedFileType() {
        return "dat";
    }
}
