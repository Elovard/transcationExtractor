package extractor;

import extractor.entity.Transaction;
import extractor.factories.ParserFactory;
import extractor.parsers.FileParser;

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
                fileParser.printAllTransactions();
                break;
            case 2:
                fileParser.printTopFiveTransactions();
                break;
            case 3:
                fileParser.printTotals();
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
