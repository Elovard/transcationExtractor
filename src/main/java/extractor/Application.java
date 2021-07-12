package extractor;

import extractor.factories.CsvFactory;
import extractor.factories.ParserFactory;
import extractor.factories.XmlFactory;
import extractor.parsers.FileParser;

import java.util.Scanner;

public class Application {
    private FileParser fileParser;

    public Application(ParserFactory factory) {
        fileParser = factory.createParser();
    }

    private static Application settingUp() {
        Application app;
        ParserFactory factory;
        Scanner scan = new Scanner(System.in);
        String filePath;

        System.out.println("Please specify the path to your file: ");
        filePath = scan.nextLine();

        if (filePath.endsWith(".xml")) {
            factory = new XmlFactory();
            app = new Application(factory);
            app.fileParser.parse(filePath);
            return app;
        }

        if (filePath.endsWith(".csv")) {
            factory = new CsvFactory();
            app = new Application(factory);
            app.fileParser.parse(filePath);
            return app;
        }

        System.out.println("Can't find your file!");
        return null;
    }


    public static void main(String[] args) {
        Application app = settingUp();
        if (app != null) {
            Scanner scan = new Scanner(System.in);

            System.out.println("Parsing xml file...");
            System.out.println("File parsed successfully");
            System.out.println("Select operation: ");
            System.out.println("[1] Print data to display \n[2] Print top-5 transactions \n[3] Print totals \n[4] Exit");

            int choice = scan.nextInt();

            switch (choice) {
                case 1:
                    app.fileParser.printAllTransactions();
                    break;
                case 2:
                    app.fileParser.printTopFiveTransactions();
                    break;
                case 3:
                    app.fileParser.printTotals();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Invalid number");
            }
            return;
        }
        System.out.println("Error occurred during parsing");
    }
}
