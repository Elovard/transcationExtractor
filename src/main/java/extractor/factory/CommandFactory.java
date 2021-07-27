package extractor.factory;

import extractor.command.Command;
import extractor.config.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
public class CommandFactory {

    private static final Logger logger = LoggerFactory.getLogger(CommandFactory.class);

    private final List<Command> commands = new ArrayList<>();

    private static final ClassPathXmlApplicationContext context = ApplicationContext.getInstance().getContext();
    private static final CommandFactory INSTANCE = new CommandFactory();

    public static CommandFactory getInstance() {
        return INSTANCE;
    }

    private CommandFactory() {
        Map<String, Command> commandsWithName = context.getBeansOfType(Command.class);
        Collection<Command> myCommands = commandsWithName.values();
        int incrementalCommandId = 1;
        for (Command command : myCommands) {
            command.setCommandId(incrementalCommandId);
            incrementalCommandId++;
            commands.add(command);
        }
        logger.info("All commands have been loaded successfully");
    }

    public List<Command> getCommands() {
        return this.commands;
    }

}
