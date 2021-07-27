package extractor.command;

import extractor.entity.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public abstract class Command {

    private int commandId;

    public abstract void execute(final List<Transaction> transactionList);

    public abstract String getCommandDescription();

    public int getCommandId() {
        return commandId;
    }

    public void setCommandId(int commandId) {
        this.commandId = commandId;
    }

    @Override
    public String toString() {
        return "[" + commandId + "]" + getCommandDescription();
    }
}
