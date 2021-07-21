package extractor;

import extractor.command.Command;
import extractor.entity.Transaction;
import extractor.exception.ApplicationException;
import extractor.parser.FileParser;

import java.util.List;
import java.util.Map;

public class Program {

    public static void main(String[] args) throws Exception {
        Application app = new Application();
        try {
            Map<String, String> arguments = app.readCommandLineArguments(args);
            String filePath = arguments.get("file");
            String extension = app.getExtension(filePath);
            FileParser parser = app.resolveParser(extension);
            List<Transaction> transactions = parser.parse(filePath);
            System.out.println("Parsing your file...");
            System.out.println("File parsed successfully");
            List<Command> commands = app.getCommands();

            while (true) {
                app.initCommandList(commands);
                int userInput = app.readUserInput();
                if (userInput == 0) {
                    break;
                }
                app.executeCommand(userInput, transactions);
            }
        } catch (ApplicationException ex) {
            System.out.println("Error occurred during execution of the program" + "\n" + ex.getMessage());
        }
    }
}
