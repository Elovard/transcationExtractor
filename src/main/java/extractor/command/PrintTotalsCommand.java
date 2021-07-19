package extractor.command;

import extractor.entity.Transaction;
import extractor.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PrintTotalsCommand extends Command {

    private static final Logger logger = LoggerFactory.getLogger(PrintTotalsCommand.class);

    private final TransactionService transactionService = TransactionService.getInstance();

    public PrintTotalsCommand(int commandId) {
        super(commandId);
    }

    @Override
    public void execute(final List<Transaction> transactions) {
        System.out.println("Total number of transactions: " + transactions.size());
        logger.info("calculating total number of transactions");

        long successful = transactionService.countSuccessfulTransactions(transactions);
        System.out.println("\tWhere successful: " + successful);

        long failed = transactionService.countFailedTransactions(transactions);
        System.out.println("\t\t\tfailed: " + failed);

        long rejected = transactionService.countRejectedTransactions(transactions);
        System.out.println("\t\t\trejected: " + rejected);


        logger.info("finishing calculating total number of transactions");
    }

    @Override
    public String getCommandDescription() {
        return "Print totals";
    }
}
