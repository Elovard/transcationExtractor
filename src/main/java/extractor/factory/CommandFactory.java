package extractor.factory;

import extractor.command.Command;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CommandFactory {
    public static final String ROOT_PACKAGE = "extractor.command";

    private final List<Command> commands = new ArrayList<>();

    private static final CommandFactory INSTANCE = new CommandFactory();

    public static CommandFactory getInstance() {
        return INSTANCE;
    }

    private CommandFactory() {
        Reflections reflections = new Reflections(ROOT_PACKAGE);
        Set<Class<? extends Command>> commandImplementations = reflections.getSubTypesOf(Command.class);

        int incrementalCommandId = 1;

        for (Class<? extends Command> impl : commandImplementations) {
            try {
                Constructor<? extends Command> constructor = impl.getConstructor(Integer.TYPE);
                Command implementation = constructor.newInstance(incrementalCommandId);

                incrementalCommandId++;

                commands.add(implementation);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Command> getCommands() {
        return this.commands;
    }

}
