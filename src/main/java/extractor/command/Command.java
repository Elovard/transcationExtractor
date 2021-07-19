package extractor.command;

import extractor.entity.Transaction;

import java.util.List;

public abstract class Command {
    // TODO: 7/19/21 add new command: sum of all failed transactions
    private final int commandId;

    public Command(int commandId) {
        this.commandId = commandId;
    }

    public abstract void execute(final List<Transaction> transactionList);

    public abstract String getCommandDescription();

    public int getCommandId() {
        return commandId;
    }

    @Override
    public String toString() {
        return "[" + commandId + "]" + getCommandDescription();
    }
}
