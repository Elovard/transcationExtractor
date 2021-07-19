package extractor.command;

import extractor.entity.Transaction;
import extractor.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PrintTopFiveTransactionsCommand extends Command {

    private static final Logger logger = LoggerFactory.getLogger(PrintTopFiveTransactionsCommand.class);
    private final TransactionService transactionService = TransactionService.getInstance();

    public PrintTopFiveTransactionsCommand(int commandId) {
        super(commandId);
    }

    @Override
    public void execute(final List<Transaction> transactions) {
        final List<Transaction> top5Transactions = transactionService.getTop5Transactions(transactions);
        System.out.println("Top 5 transactions:");
        top5Transactions.forEach(System.out::println);
    }

    @Override
    public String getCommandDescription() {
        return "Print top-5 transactions";
    }
}
