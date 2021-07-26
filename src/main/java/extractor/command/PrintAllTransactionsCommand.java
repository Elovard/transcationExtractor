package extractor.command;

import extractor.entity.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PrintAllTransactionsCommand extends Command {

    private static final Logger logger = LoggerFactory.getLogger(PrintAllTransactionsCommand.class);

    public PrintAllTransactionsCommand() {

    }

    @Override
    public void execute(final List<Transaction> transactions) {
        System.out.println("Here's the list of transactions: ");
        transactions.forEach(System.out::println);
    }

    @Override
    public String getCommandDescription() {
        return "Print all transactions";
    }
}
