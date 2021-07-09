package extractor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public abstract class CsvParser {
    private static Scanner scan = new Scanner(System.in);
    private static List<Transaction> listOfTransactions = new ArrayList<>();

    public static void settingUp() {
        System.out.println("Parsing xml file...");
        System.out.println("File parsed successfully");
        System.out.println("Select operation: ");
        System.out.println("[1] Print data to display \n[2] Print top-5 transactions \n[3] Print totals \n[4] Exit");
        int choice = scan.nextInt();

        switch (choice) {
            case 1:
                CsvParser.printCsvTransactions();
                break;
            case 2:
                CsvParser.printTopFiveTransactions();
                break;
            case 3:
                CsvParser.printScvTotals();
                break;
            case 4:
                break;
            default:
                System.out.println("Invalid number");
        }
    }

    public static void parseCsv(String filePath) {
        Path path = Paths.get(filePath);
        try {
            List<String> contents = Files.readAllLines(path);
            contents.remove(0);

            for (String content : contents) {
                String[] parsedData = content.split(",");
                //todo create method "Transaction parseLine(String csvLine)
                listOfTransactions.add(new Transaction(
                        parsedData[1],  // TODO: 7/9/21 why so
                        parsedData[2],
                        parsedData[0],
                        parsedData[3],
                        parsedData[4],
                        parsedData[5]));
            }
        } catch (IOException ex) {
            System.out.println("Can't find your file!");
        }
    }

    public static void printCsvTransactions() {
        System.out.println("Here's the list of transactions: ");
        for (Transaction transaction : listOfTransactions) {
            System.out.println(transaction);
        }
    }

    public static void printTopFiveTransactions() {
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

    public static void printScvTotals() {
        System.out.println("Total number of transactions: " + listOfTransactions.size());

        int successful = 0;
        int failed = 0;
        int rejected = 0;

        for (Transaction transaction : listOfTransactions) {
            if (transaction.getTransactionResult().equalsIgnoreCase("success")) {
                successful++;
            }
        }
        System.out.println("\tWhere successful: " + successful);

        for (Transaction transaction : listOfTransactions) {
            if (transaction.getTransactionResult().equalsIgnoreCase("failed")) {
                failed++;
            }
        }
        System.out.println("\t\t\tfailed: " + failed);

        for (Transaction transaction : listOfTransactions) {
            if (transaction.getTransactionResult().equalsIgnoreCase("rejected")) {
                rejected++;
            }
        }
        System.out.println("\t\t\trejected: " + rejected);
    }
}
