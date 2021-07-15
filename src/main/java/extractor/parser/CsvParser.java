package extractor.parser;

import extractor.entity.Transaction;
import extractor.entity.TransactionStatus;
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

public class CsvParser implements FileParser {

    private static final List<Transaction> listOfTransactions = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(CsvParser.class);

    @Override
    public List<Transaction> parse(String filePath) throws Exception {
        logger.info("entering parse method in CsvParser");
        Path path = Paths.get(filePath);
        try {
            List<String> rows = Files.readAllLines(path);
            rows.remove(0);

            for (String row : rows) {
                listOfTransactions.add(parseOneLine(row));
            }
        } catch (IOException ex) {
            logger.error("Can't find this file");
            throw new Exception("Can't find your file!");
        }
        logger.info("parsed successfully");
        return listOfTransactions;
    }

    public Transaction parseOneLine(String line) {
        logger.info("parsing by lines in CsvParser");
        String[] parsedData = line.split(",");

        long parsedLong = Long.parseLong(parsedData[0]);
        Timestamp timestamp = new Timestamp(parsedLong);
        Date date = new Date(timestamp.getTime());

        logger.info("creating new transaction object");
        return new Transaction(
                date,
                parsedData[1],
                parsedData[2],
                Double.parseDouble(parsedData[3]),
                parsedData[4],
                TransactionStatus.valueOf(parsedData[5].toUpperCase()));
    }

    @Override
    public String getSupportedFileType() {
        return "csv";
    }
}
