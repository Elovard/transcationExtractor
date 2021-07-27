package extractor.command;

import extractor.entity.Transaction;
import extractor.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PrintSumOfFailedTransactions extends Command {

    private static final Logger logger = LoggerFactory.getLogger(PrintTopFiveTransactionsCommand.class);

    private final TransactionService transactionService;

    public PrintSumOfFailedTransactions(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public void execute(List<Transaction> transactionList) {
        final int sumOfFailedTransactions = transactionService.countSumOfFailedTransactions(transactionList);
        System.out.println("Sum of failed transactions: " + sumOfFailedTransactions);
    }

    @Override
    public String getCommandDescription() {
        return "Print sum of failed transactions";
    }
}
