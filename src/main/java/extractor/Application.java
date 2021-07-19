package extractor;

import extractor.command.Command;
import extractor.entity.Transaction;
import extractor.factory.CommandFactory;
import extractor.factory.ParserFactory;
import extractor.parser.FileParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    private final Map<Integer, Command> listOfCommands = new HashMap<>();

    public static void main(String[] args) throws Exception {
        Application app = new Application();
        CommandFactory commandFactory = new CommandFactory();
        Scanner scan = new Scanner(System.in);

        String path = args[0];
        logger.info("received path from console");
        String extension = app.getExtension(path);
        logger.info("received extension from method getExtension");

        ParserFactory factory = new ParserFactory();
        FileParser fileParser = factory.createParser(extension);
        logger.info("created parser");

        List<Transaction> transactionList = null;

        try {
            transactionList = fileParser.parse(path);
        } catch (NullPointerException ex) {
            System.out.println("Unsupported type of file!");
            logger.error("received unsupported file extension");
            throw new Exception("Unsupported type of file!");
        }

        logger.info("successfully parsed file");
        System.out.println("Parsing xml file...");
        System.out.println("File parsed successfully");
        app.printCommands(commandFactory.getCommands());

        while (true) {
            System.out.println("Select operation: ");
            int choice = scan.nextInt();

            if (choice == 0) {
                break;
            }

            try {
                Command command = app.listOfCommands.get(choice);
                command.execute(transactionList);
            } catch (NullPointerException ex) {
                System.out.println("Invalid command");
                logger.error("received invalid command from input");
            }
        }
    }

    public String getExtension(String path) throws Exception {
        Pattern pattern = Pattern.compile("\\.([\\w]+)$");
        Matcher matcher = pattern.matcher(path);
        String result = null;
        if (path.isEmpty()) {
            logger.error("invalid file name");
            throw new Exception("Invalid file name");
        }
        if (matcher.find()) {
            result = matcher.group(1);
        }
        return result;
    }

    private void printCommands(List<Command> commands) {
        System.out.println("The List of available commands: \n[0]Exit");
        for (Command command : commands) {
            listOfCommands.put(command.getCommandId(), command);
            System.out.println(command.toString());
        }
    }

}
