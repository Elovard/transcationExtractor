package extractor;

import extractor.entity.Transaction;
import extractor.entity.TransactionStatus;
import extractor.factory.ParserFactory;
import extractor.parser.FileParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Application {

    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter path: ");
        String path = scan.nextLine();
        logger.info("received path from input");
        String extension = getExtension(path);
        logger.info("received extension from method getExtension");

        ParserFactory factory = new ParserFactory();

        FileParser fileParser = factory.createParser(extension);
        logger.info("created parser");
        List<Transaction> transactionList = fileParser.parse(path);
        logger.info("successfully parsed file");

        System.out.println("Parsing xml file...");
        System.out.println("File parsed successfully");
        System.out.println("Select operation: ");
        System.out.println("[1] Print data to display \n[2] Print top-5 transactions \n[3] Print totals \n[4] Exit");

        int choice = scan.nextInt();
        logger.info("reading choice from input");

        switch (choice) {
            case 1:
                System.out.println("Here's the list of transactions: ");
                logger.info("looping through the list of transactions");
                transactionList.forEach(System.out::println);
                logger.info("showed the list of transactions");
                break;
            case 2:
                List<Double> amountsOfTransactions = new ArrayList<>();
                logger.info("created list with amounts of transactions");
                for (Transaction transaction : transactionList) {
                    amountsOfTransactions.add(transaction.getAmount());
                    logger.info("adding amounts of transaction to the list");
                }
                if (amountsOfTransactions.size() < 5) {
                    System.out.println("The list of transactions contains less than 5 transactions");
                    logger.warn("received less than 5 transactions");
                    break;
                }
                logger.info("sorting the list with amounts of transactions");
                amountsOfTransactions.sort(Comparator.reverseOrder());
                System.out.println(
                        "Top-5 transactions: " + amountsOfTransactions.get(0) + ", " +
                                amountsOfTransactions.get(1) + ", " +
                                amountsOfTransactions.get(2) + ", " +
                                amountsOfTransactions.get(3) + ", " +
                                amountsOfTransactions.get(4));
                logger.info("showed top-5 transactions");
                break;
            case 3:
                System.out.println("Total number of transactions: " + transactionList.size());
                logger.info("calculating total number of transactions");

                long successful = transactionList
                        .stream()
                        .filter(c -> c.getTransactionResult().equals(TransactionStatus.SUCCESS) ||
                                c.getTransactionResult().equals(TransactionStatus.COMPLETE))
                        .count();
                System.out.println("\tWhere successful: " + successful);
                logger.info("successful transactions: " + successful);

                long failed = transactionList
                        .stream()
                        .filter(c -> c.getTransactionResult().equals(TransactionStatus.FAILED) ||
                                c.getTransactionResult().equals(TransactionStatus.FAILURE))
                        .count();
                System.out.println("\t\t\tfailed: " + failed);
                logger.info("failed transactions: " + failed);

                long rejected = transactionList
                        .stream()
                        .filter(c -> c.getTransactionResult().equals(TransactionStatus.REJECTED))
                        .count();
                System.out.println("\t\t\trejected: " + rejected);
                logger.info("rejected transactions: " + rejected);


                logger.info("finishing calculating total number of transactions");
                break;
            case 4:
                logger.info("received 4 - exiting from the application");
                break;
            default:
                logger.error("received incorrect guiding number, exiting");
                System.out.println("Invalid number");
        }
    }

    public static String getExtension(String path) throws Exception {
        StringBuilder sb = new StringBuilder(path);
        logger.info("extracting extension from file path");
        String result = null;
        try {
            result = sb.delete(0, sb.length() - 3).toString();
        } catch (StringIndexOutOfBoundsException ex) {
            logger.error("received path with length 0 or less");
            System.out.println("Invalid path with length 0 or less");
        }
        return result;
    }
}
