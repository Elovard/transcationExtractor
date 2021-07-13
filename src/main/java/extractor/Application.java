package extractor;

import extractor.entity.Transaction;
import extractor.entity.TransactionStatus;
import extractor.factory.ParserFactory;
import extractor.parser.FileParser;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Application {


    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter path: ");
        String path = scan.nextLine();
        String extension = getExtension(path);

        FileParser fileParser = ParserFactory.createParser(extension);
        List<Transaction> transactionList = fileParser.parse(path);

        System.out.println("Parsing xml file...");
        System.out.println("File parsed successfully");
        System.out.println("Select operation: ");
        System.out.println("[1] Print data to display \n[2] Print top-5 transactions \n[3] Print totals \n[4] Exit");

        int choice = scan.nextInt();

        switch (choice) {
            case 1:
                System.out.println("Here's the list of transactions: ");
                transactionList.forEach(System.out::println);
                break;
            case 2:
                List<Double> amountsOfTransactions = new ArrayList<>();
                for (Transaction transaction : transactionList) {
                    amountsOfTransactions.add(transaction.getAmount());
                }
                if (amountsOfTransactions.size() < 5) {
                    System.out.println("The list of transactions contains less than 5 transactions");
                    break;
                }
                amountsOfTransactions.sort(Comparator.reverseOrder());
                System.out.println(
                        "Top-5 transactions: " + amountsOfTransactions.get(0) + ", " +
                                amountsOfTransactions.get(1) + ", " +
                                amountsOfTransactions.get(2) + ", " +
                                amountsOfTransactions.get(3) + ", " +
                                amountsOfTransactions.get(4));
                break;
            case 3:
                System.out.println("Total number of transactions: " + transactionList.size());

                int successful = 0;
                int failed = 0;
                int rejected = 0;

                for (Transaction transaction : transactionList) {
                    if (transaction.getTransactionResult().equals(TransactionStatus.valueOf("SUCCESS")) ||
                            transaction.getTransactionResult().equals(TransactionStatus.valueOf("COMPLETE"))) {
                        successful++;
                    }
                }
                System.out.println("\tWhere successful: " + successful);

                for (Transaction transaction : transactionList) {
                    if (transaction.getTransactionResult().equals(TransactionStatus.valueOf("FAILED")) ||
                            transaction.getTransactionResult().equals(TransactionStatus.valueOf("FAILURE"))) {
                        failed++;
                    }
                }

                System.out.println("\t\t\tfailed: " + failed);

                for (Transaction transaction : transactionList) {
                    if (transaction.getTransactionResult().equals(TransactionStatus.valueOf("REJECTED"))) {
                        rejected++;
                    }
                }
                System.out.println("\t\t\trejected: " + rejected);
                break;
            case 4:
                break;
            default:
                System.out.println("Invalid number");
        }
    }

    public static String getExtension(String path) {
        StringBuilder sb = new StringBuilder(path);
        return sb.delete(0, sb.length() - 3).toString();
    }

}
