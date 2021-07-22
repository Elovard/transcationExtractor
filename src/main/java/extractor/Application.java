package extractor;

import extractor.command.Command;
import extractor.entity.Transaction;
import extractor.exception.ApplicationException;
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

    public static final int EXIT_COMMAND_ID = 0;

    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    private final Map<Integer, Command> commands = new HashMap<>();

    public Map<String, String> readCommandLineArguments(String[] args) throws ExtensionResolvingException {
        Map<String, String> arguments = new HashMap<>();
        for (String argument : args) { // file=/file.csv lol=kek=kek vlad=molodec currencyRate=1.345 currencyCode=eur
            String[] argumentParts = argument.split("=");

            if (argumentParts.length != 2) {
                logger.warn("Found invalid argument {}, skipping it and moving to the next one", argument);
                continue;
            }

            String argumentName = argumentParts[0];
            String argumentValue = argumentParts[1];

            if (arguments.containsKey(argumentName)) {
                logger.warn("Found duplicate argument with name: {}, argument {} will be ignored", argumentName, argument);
                continue;
            }

            arguments.put(argumentName, argumentValue);
        }

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

    public int readUserInput() throws ApplicationException {
        System.out.println("Select operation: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            String choiceAsString = reader.readLine();
            return Integer.parseInt(choiceAsString);
        } catch (IOException | NumberFormatException e) {
            throw new ApplicationException("An error occured while reading user's input: " + e.getMessage());
        }
    }

    public void executeCommand(int commandId, List<Transaction> transactions) throws ApplicationException {
        if (!commands.containsKey(commandId)) {
           throw new ApplicationException("Command with given id: " + commandId + " not found");
        }

        Command command = commands.get(commandId);
        command.execute(transactions);
    }

    public String getExtension(String path) throws ExtensionResolvingException {
        if (path == null || path.isEmpty()) {
            logger.error("Invalid file path: {}", path);
            throw new ExtensionResolvingException("Invalid file path: " + path);
        }

        Pattern pattern = Pattern.compile("\\.([\\w]+)$");
        Matcher matcher = pattern.matcher(path);

        if (!matcher.find()) {
            throw new ExtensionResolvingException("Unable to resolve extension from path: " + path);
        }

        return matcher.group(1);
    }

    public void printCommands() {
        System.out.println("The List of available commands: \n[0]Exit");
        for (Command command : commands.values()) {
            System.out.println(command);
        }
    }

    public void initCommands() {
        CommandFactory commandFactory = new CommandFactory(); // make CommandFactory as singleton
        logger.info("initializing list of commands");
        List<Command> commandList = commandFactory.getCommands();

        for (Command command : commandList) {
            this.commands.put(command.getCommandId(), command);
        }
    }
}
