package extractor;

import extractor.command.Command;
import extractor.entity.Transaction;
import extractor.exception.ExtensionResolvingException;
import extractor.factory.CommandFactory;
import extractor.factory.ParserFactory;
import extractor.parser.FileParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    private final Map<Integer, Command> listOfCommands = new HashMap<>();

    public Map<String, String> readCommandLineArguments(String[] args) throws ExtensionResolvingException {
        int filesFound = 0;
        Map<String, String> arguments = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("file=")) {
                String[] filePath = args[i].split("=");
                String key = filePath[0];
                String value = filePath[1];
                arguments.put(key, value);
                filesFound++;
                logger.info("found a file");
            }
        }
        if (filesFound > 1) {
            logger.error("found more than one file");
            throw new ExtensionResolvingException("Find more than one file!");
        }
        if (filesFound == 0) {
            logger.error("no files found");
            throw new ExtensionResolvingException("Can't find your file!");
        }
        System.out.println(arguments.get("file") + " is a file");
        return arguments;
    }

    public FileParser resolveParser(String extension) throws ExtensionResolvingException {
        logger.info("received extension from method getExtension");
        ParserFactory factory = new ParserFactory();
        FileParser fileParser = factory.createParser(extension);

        if (fileParser == null) {
            logger.error("received unsupported type of file!");
            throw new ExtensionResolvingException("Unsupported type of file!");
        }

        logger.info("created parser");
        return fileParser;
    }

    public List<Command> getCommands() {
        CommandFactory commandFactory = new CommandFactory();
        logger.info("initializing list of commands");
        return commandFactory.getCommands();
    }

    public int readUserInput() throws IOException {
        System.out.println("Select operation: ");
        printCommands();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String choiceAsString = reader.readLine();
        return Integer.parseInt(choiceAsString);
    }

    public void executeCommand(int userInput, List<Transaction> transactions) {
        try {
            Command command = listOfCommands.get(userInput);
            command.execute(transactions);
        } catch (NullPointerException ex) {
            System.out.println("Invalid command");
            logger.error("received invalid command from input");
        }
    }

    public String getExtension(String path) throws Exception {
        Pattern pattern = Pattern.compile("\\.([\\w]+)$");
        Matcher matcher = pattern.matcher(path);
        String result = null;
        if (path.isEmpty()) {
            logger.error("invalid file name");
            throw new ExtensionResolvingException("Invalid file name");
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
