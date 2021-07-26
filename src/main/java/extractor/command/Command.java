package extractor.command;

import extractor.entity.Transaction;

import java.util.List;

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
