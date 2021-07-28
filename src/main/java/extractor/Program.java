package extractor;

import extractor.entity.Transaction;
import extractor.exception.ApplicationException;
import extractor.parser.FileParser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Map;

public class Program {

    public static void main(String[] args) throws Exception {

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Application app = context.getBean(Application.class);

        try {
            Map<String, String> arguments = app.parseCommandLineArguments(args);

            String filePath = arguments.get("file");

            String extension = app.getExtension(filePath);

            FileParser parser = app.resolveParser(extension);

            System.out.println("Parsing your file...");
            List<Transaction> transactions = parser.parse(filePath);

            System.out.println("File parsed successfully");

            app.initCommands();

            while (true) {
                app.printCommands();

                int commandId = app.readUserInput();
                if (Application.EXIT_COMMAND_ID == commandId) {
                    break;
                }
                app.executeCommand(commandId, transactions);
            }
        } catch (ApplicationException ex) {
            System.out.println("Error occurred during execution of the program" + "\n" + ex.getMessage());
        }
    }
}
