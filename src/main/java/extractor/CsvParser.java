package extractor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class CsvParser implements FileParser {

    private static Scanner scan = new Scanner(System.in);
    private static List<Transaction> listOfTransactions = new ArrayList<>();

    @Override
    public void parse(String filePath) {
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
    }

    public Transaction parseOneLine(String line) {
        String[] parsedData = line.split(",");
        return new Transaction(
                parsedData[0],
                parsedData[1],
                parsedData[2],
                parsedData[3],
                parsedData[4],
                TransactionStatus.valueOf(parsedData[5].toUpperCase()));
    }

    @Override
    public List<Transaction> printAllTransactions() {
        System.out.println("Here's the list of transactions: ");
        listOfTransactions.forEach(System.out::println);
        return listOfTransactions;
    }

    @Override
    public void printTopFiveTransactions() {
        List<Integer> amountsOfTransactions = new ArrayList<>();
        for (Transaction transaction : listOfTransactions) {
            amountsOfTransactions.add(Integer.parseInt(transaction.getAmount()));
        }
        if (amountsOfTransactions.size() < 5) {
            System.out.println("The list of transactions contains less than 5 transactions");
            return;
        }
        amountsOfTransactions.sort(Comparator.reverseOrder());
        System.out.println(
                "Top-5 transactions: " + amountsOfTransactions.get(0) + ", " +
                        amountsOfTransactions.get(1) + ", " +
                        amountsOfTransactions.get(2) + ", " +
                        amountsOfTransactions.get(3) + ", " +
                        amountsOfTransactions.get(4));
    }

    @Override
    public void printTotals() {
        System.out.println("Total number of transactions: " + listOfTransactions.size());

        int successful = 0;
        int failed = 0;
        int rejected = 0;

        for (Transaction transaction : listOfTransactions) {
            if (transaction.getTransactionResult().equals(TransactionStatus.valueOf("SUCCESS"))) {
                successful++;
            }
        }
        System.out.println("\tWhere successful: " + successful);

        for (Transaction transaction : listOfTransactions) {
            if (transaction.getTransactionResult().equals(TransactionStatus.valueOf("FAILED"))) {
                failed++;
            }
        }

        System.out.println("\t\t\tfailed: " + failed);

        for (Transaction transaction : listOfTransactions) {
            if (transaction.getTransactionResult().equals(TransactionStatus.valueOf("REJECTED"))) {
                rejected++;
            }
        }
        System.out.println("\t\t\trejected: " + rejected);
    }
}
