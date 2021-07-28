package extractor.factory;

import extractor.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandFactory {

    private static final Logger logger = LoggerFactory.getLogger(CommandFactory.class);

    @Autowired
    private final List<Command> commands;

    public CommandFactory(List<Command> commands) {
        this.commands = commands;
        init();
        logger.info("Created CommandFactory");
    }

    private void init() {
        int incrementalCommandId = 1;
        for (Command command : commands) {
            command.setCommandId(incrementalCommandId++);
        }
        logger.info("Finishing initializing commands");
    }

    public List<Command> getCommands() {
        return this.commands;
    }

}
