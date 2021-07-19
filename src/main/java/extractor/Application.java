package extractor;

import extractor.command.Command;
import extractor.entity.Transaction;
import extractor.exception.TransactionException;
import extractor.factory.CommandFactory;
import extractor.factory.ParserFactory;
import extractor.parser.FileParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    private final Map<Integer, Command> listOfCommands = new HashMap<>();

    public void settingUp(String path) throws Exception {
        CommandFactory commandFactory = new CommandFactory();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        logger.info("received path from console");
        String extension = Application.getExtension(path);
        logger.info("received extension from method getExtension");

        ParserFactory factory = new ParserFactory();
        FileParser fileParser = factory.createParser(extension);

        if (fileParser == null) {
            logger.error("received unsupported type of file!");
            throw new TransactionException("Unsupported type of file!");
        }

        logger.info("created parser");
        List<Transaction> transactionList = null;
        transactionList = fileParser.parse(path);


        logger.info("successfully parsed file");
        System.out.println("Parsing xml file...");
        System.out.println("File parsed successfully");

        initCommandList(commandFactory.getCommands());
        printCommands();

        while (true) {
            System.out.println("Select operation: ");
            String choiceAsString = reader.readLine();
            int choice = Integer.parseInt(choiceAsString);

            if (choice == 0) {
                break;
            }

            try {
                Command command = listOfCommands.get(choice);
                command.execute(transactionList);
            } catch (NullPointerException ex) {
                System.out.println("Invalid command");
                logger.error("received invalid command from input");
            }
        }
    }

    public static String getExtension(String path) throws Exception {
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

    public void printCommands() {
        System.out.println("The List of available commands: \n[0]Exit");
        for (Command command : listOfCommands.values()) {
            System.out.println(command);
        }
    }

    public void initCommandList(List<Command> commands) {
        logger.info("initializing the list of commands");
        for (Command command : commands) {
            listOfCommands.put(command.getCommandId(), command);
        }
    }
}
