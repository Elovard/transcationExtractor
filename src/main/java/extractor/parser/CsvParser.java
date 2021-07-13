package extractor.parser;

import extractor.entity.Transaction;
import extractor.entity.TransactionStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CsvParser implements FileParser {

    private static List<Transaction> listOfTransactions = new ArrayList<>();

    @Override
    public List<Transaction> parse(String filePath) {
        Path path = Paths.get(filePath);
        try {
            List<String> rows = Files.readAllLines(path);
            rows.remove(0);

            for (String row : rows) {
                listOfTransactions.add(parseOneLine(row));
            }
        } catch (IOException ex) {
            System.out.println("Can't find your file!");
        }
        return listOfTransactions;
    }

    public Transaction parseOneLine(String line) {
        String[] parsedData = line.split(",");

        long parsedLong = Long.parseLong(parsedData[0]);
        Timestamp timestamp = new Timestamp(parsedLong);
        Date date = new Date(timestamp.getTime());

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
